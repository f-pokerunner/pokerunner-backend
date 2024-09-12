package com.flab.pokerunner.service.running;

import com.flab.pokerunner.core.GateWay;
import com.flab.pokerunner.domain.command.running.StartRunningCommand;
import com.flab.pokerunner.domain.command.running.StopRunningCommand;
import com.flab.pokerunner.domain.dto.pokemon.UserPokemonDao;
import com.flab.pokerunner.domain.dto.response.StopRunningResponse;
import com.flab.pokerunner.domain.dto.running.UserRunningSummaryDto;
import com.flab.pokerunner.repository.pokemon.UserPokemonJdbcRepository;
import com.flab.pokerunner.service.NHNMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class RunningService {

    private final ConcurrentHashMap<Integer, RunningSimulator> simulators = new ConcurrentHashMap<>();
    private final GateWay gateWay;
    private final NHNMapService NHNMapService;
    private final UserPokemonJdbcRepository userPokemonJdbcRepository;

    public void start(StartRunningCommand command) {
        int userId = command.getUserId();

        RunningSimulator simulator = new RunningSimulator(command, gateWay, NHNMapService);
        simulators.put(userId, simulator);
        simulator.start();
    }

    public StopRunningResponse stop(StopRunningCommand command) {
        int userId = command.getUserId();
        RunningSimulator simulator = simulators.remove(userId);
        UserRunningSummaryDto userRunningSummaryDto = null;
        if (simulator != null) {
            userRunningSummaryDto = simulator.stop();
        }

        UserPokemonDao userPokemonDao = userPokemonJdbcRepository.findUserPokemonByUserId(command.userId);
        return StopRunningResponse.from(userPokemonDao, Objects.requireNonNull(userRunningSummaryDto));
    }
}
