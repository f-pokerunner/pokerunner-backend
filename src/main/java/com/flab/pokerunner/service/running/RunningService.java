package com.flab.pokerunner.service.running;

import com.flab.pokerunner.core.GateWay;
import com.flab.pokerunner.domain.command.running.StartRunningCommand;
import com.flab.pokerunner.domain.command.running.StopRunningCommand;
import com.flab.pokerunner.service.NHNMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RunningService {

    private final ConcurrentHashMap<Integer, RunningSimulator> simulators = new ConcurrentHashMap<>();
    private final GateWay gateWay;
    private final NHNMapService NHNMapService;

    public void start(StartRunningCommand command) {
        int userId = command.getUserId();

        RunningSimulator simulator = new RunningSimulator(command, gateWay, NHNMapService);
        simulators.put(userId, simulator);
        simulator.start();
    }

    public void stop(StopRunningCommand command) {
        int userId = command.getUserId();
        RunningSimulator simulator = simulators.remove(userId);
        if (simulator != null) {
            simulator.stop();
        }
    }
}
