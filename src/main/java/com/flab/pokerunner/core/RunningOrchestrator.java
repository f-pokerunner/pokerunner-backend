package com.flab.pokerunner.core;

import com.flab.pokerunner.domain.dto.PokemonLocationDto;
import com.flab.pokerunner.domain.entity.UserRunningJpo;
import com.flab.pokerunner.domain.event.running.PokemonSearched;
import com.flab.pokerunner.domain.event.running.RunningStarted;
import com.flab.pokerunner.domain.event.running.RunningStopped;
import com.flab.pokerunner.service.PokemonSpotStore;
import com.flab.pokerunner.service.pokemon.PokemonStore;
import com.flab.pokerunner.service.running.RunningStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class RunningOrchestrator {

    private final GateWay gateWay;
    private final RunningStore runningStore;
    private final PokemonStore pokemonStore;
    private final PokemonSpotStore pokemonSpotStore;
    private final Map<Integer, Integer> userRunningMap = new HashMap<>();

    @EventListener
    public void on(RunningStarted event) {
        log.info("달리기 시작 이벤트 :{}", event);
        int savedJpoId = runningStore.save(new UserRunningJpo(event));
        userRunningMap.put(event.getUserId(), savedJpoId);
    }

    @EventListener
    public void on(RunningStopped event) {
        log.info("달리기 종료 이벤트:{}", event);
        int userId = event.getUserId();
        Integer runningId = userRunningMap.get(userId);

        if (runningId != null) {
            UserRunningJpo foundRecordJpo = runningStore.findById(runningId);
            if (foundRecordJpo != null) {
                foundRecordJpo.updateAfterEndRunning(event);
                runningStore.save(foundRecordJpo);
            }
            pokemonStore.save(event);
            userRunningMap.remove(userId);
        }
    }

    @EventListener
    public void on(PokemonSearched event) {
        List<PokemonLocationDto> foundPokemons = pokemonSpotStore.searchPokemon(event);
        foundPokemons.forEach(pokemon -> {
            log.info("{} 이 찾은 포켓몬 이름:{}", event.getUserId(), pokemon.getPokemonName());
            pokemonStore.save(event, pokemon);
        });
    }
}
