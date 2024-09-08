package com.flab.pokerunner.service.running;

import com.flab.pokerunner.core.GateWay;
import com.flab.pokerunner.domain.event.running.RunningStarted;
import com.flab.pokerunner.domain.event.running.RunningStopped;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class RunningSimulator {
    private double currentLat;
    private double currentLng;
    private final double direction;
    private final double speed;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private ScheduledExecutorService executor;

    private long startTime;
    private double totalDistance;

    public RunningSimulator(String startLat, String startLng) {
        this.currentLat = Double.parseDouble(startLat);
        this.currentLng = Double.parseDouble(startLng);
        this.direction = new Random().nextDouble() * 2 * Math.PI;
        this.speed = 5.56;
        this.totalDistance = 0.0;
    }

    public void start(int userId, GateWay gateWay) {
        if (isRunning.compareAndSet(false, true)) {
            startTime = System.currentTimeMillis();
            executor = Executors.newSingleThreadScheduledExecutor();
            executor.scheduleAtFixedRate(this::updatePosition, 0, 3, TimeUnit.SECONDS);

            gateWay.publish(new RunningStarted(userId, LocalDateTime.now()));
        }
    }

    public void stop(int userId, GateWay gateWay) {
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
            log.info("러닝 페이스: {}", pace);

            double averageSpeed = distanceKm / durationHours;
            log.info("평균 속도: {} km/h", averageSpeed);

            gateWay.publish(new RunningStopped(userId, totalDistance, averageSpeed, pace, LocalDateTime.now()));
        }
    }

    private void updatePosition() {
        double latChange = speed * Math.cos(direction) / 111000.0;
        double lngChange = speed * Math.sin(direction) / (111000.0 * Math.cos(Math.toRadians(currentLat)));

        currentLat += latChange;
        currentLng += lngChange;

        totalDistance += speed * 3;

        log.info("Current position: {}, {}", currentLat, currentLng);
    }
}