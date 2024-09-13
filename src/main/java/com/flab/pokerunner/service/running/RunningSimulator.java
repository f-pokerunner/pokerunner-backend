package com.flab.pokerunner.service.running;

import com.flab.pokerunner.core.GateWay;
import com.flab.pokerunner.domain.command.running.StartRunningCommand;
import com.flab.pokerunner.domain.dto.nhn.LocationDto;
import com.flab.pokerunner.domain.dto.running.UserRunningSummaryDto;
import com.flab.pokerunner.domain.event.running.PokemonSearched;
import com.flab.pokerunner.domain.event.running.RunningStarted;
import com.flab.pokerunner.domain.event.running.RunningStopped;
import com.flab.pokerunner.handler.LocationWebSocketHandler;
import com.flab.pokerunner.service.NHNMapService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class RunningSimulator {
    private final double direction;
    private final double speed;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private final NHNMapService NHNMapService;
    private final GateWay gateWay;
    private final int userId;
    private final LocationWebSocketHandler locationWebSocketHandler;
    HashMap<Integer, String> userLocation = new HashMap<>();
    private double currentLat;
    private double currentLng;
    private ScheduledExecutorService executor;
    private long startTime;
    private double totalDistance;

    public RunningSimulator(StartRunningCommand command,
                            GateWay gateWay,
                            NHNMapService NHNMapService,
                            LocationWebSocketHandler locationWebSocketHandler) {
        this.currentLat = Double.parseDouble(command.lat);
        this.currentLng = Double.parseDouble(command.lon);
        this.direction = new Random().nextDouble() * 2 * Math.PI;
        this.speed = 100.0; // 30 m/s (108 km/h)
        this.totalDistance = 0.0;
        this.NHNMapService = NHNMapService;
        this.gateWay = gateWay;
        this.userId = command.getUserId();
        this.locationWebSocketHandler = locationWebSocketHandler;
    }

    public void start() {
        if (isRunning.compareAndSet(false, true)) {
            LocationDto locationData = NHNMapService.getLocationData(currentLat, currentLng);
            String guAddress = locationData.getLocation().getAdmAddress().getAddressCategory2();

            userLocation.put(userId, guAddress);

            startTime = System.currentTimeMillis();
            executor = Executors.newSingleThreadScheduledExecutor();
            executor.scheduleAtFixedRate(this::updatePosition, 0, 3, TimeUnit.SECONDS);

            gateWay.publish(new RunningStarted(userId, LocalDateTime.now(), guAddress));
        }
    }

    public UserRunningSummaryDto stop() {
        if (isRunning.compareAndSet(true, false)) {
            executor.shutdown();
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;

            double distanceKm = totalDistance / 1000.0;
            double durationHours = duration / 3600000.0;

            log.info("달리기 종료: {}", userId);
            log.info("총 달린 거리: {} 미터", totalDistance);

            double paceMinutesPerKm = (duration / 60000.0) / distanceKm;
            int paceMinutes = (int) paceMinutesPerKm;
            int paceSeconds = (int) ((paceMinutesPerKm - paceMinutes) * 60);

            String pace = String.format("%d'%02d\"/km", paceMinutes, paceSeconds);
            log.info("페이스: {}", pace);

            double averageSpeed = distanceKm / durationHours;
            log.info("평균 속도: {} km/h", averageSpeed);

            gateWay.publish(new RunningStopped(userId, totalDistance, averageSpeed, pace, LocalDateTime.now()));

            return new UserRunningSummaryDto(totalDistance, pace, averageSpeed, duration);
        }
        return null;
    }

    private void updatePosition() {
        double latChange = speed * Math.cos(direction) / 111000.0;
        double lngChange = speed * Math.sin(direction) / (111000.0 * Math.cos(Math.toRadians(currentLat)));

        LocationDto locationData = NHNMapService.getLocationData(currentLat, currentLng);
        String address = locationData.getLocation().getLegalAddress().getAddress();
        String guAddress = locationData.getLocation().getAdmAddress().getAddressCategory2();
        String foundAddress = userLocation.get(userId);

        if (!guAddress.equals(foundAddress)) {
            stop();
        }

        log.info("현재 위치: {}, {}, {}", currentLat, currentLng, address);
        String message = String.format("{\"lat\": %f, \"lng\": %f}", currentLat, currentLng);

        try {
            locationWebSocketHandler.sendMessage(message);
        } catch (IOException e) {
            log.error("Failed to send WebSocket message", e);
        }
        gateWay.publish(PokemonSearched.of(userId, currentLat, currentLng));

        currentLat += latChange;
        currentLng += lngChange;
        totalDistance += speed * 3;
    }
}