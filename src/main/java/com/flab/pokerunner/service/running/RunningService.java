package com.flab.pokerunner.service.running;

import com.flab.pokerunner.core.GateWay;
import com.flab.pokerunner.domain.command.running.StartRunningCommand;
import com.flab.pokerunner.domain.command.running.StopRunningCommand;
import com.flab.pokerunner.domain.dto.LocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RunningService {

    private final ConcurrentHashMap<Integer, RunningSimulator> simulators = new ConcurrentHashMap<>();
    private final GateWay gateWay;
    private final Environment env;

    public void start(StartRunningCommand command) {
        int userId = command.getUserId();
        String startLat = command.getLat();
        String startLng = command.getLon();

        RunningSimulator simulator = new RunningSimulator(startLat, startLng);
        simulators.put(userId, simulator);
        simulator.start(userId, gateWay);
    }

    public void stop(StopRunningCommand command) {
        int userId = command.getUserId();
        RunningSimulator simulator = simulators.remove(userId);
        if (simulator != null) {
            simulator.stop(userId, gateWay);
        }
    }
}
