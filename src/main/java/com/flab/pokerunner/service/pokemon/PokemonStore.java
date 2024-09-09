package com.flab.pokerunner.service.pokemon;

import com.flab.pokerunner.domain.entity.UserPokemonJpo;
import com.flab.pokerunner.domain.event.running.RunningStopped;
import com.flab.pokerunner.repository.pokemon.UserPokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PokemonStore {
    private final UserPokemonRepository userPokemonRepository;

    public void save(RunningStopped event) {
        UserPokemonJpo foundPokemon = userPokemonRepository.findByUserId(event.getUserId());
        if (foundPokemon != null) {
            int totalDistanceMeter = Double.valueOf(event.getTotalDistanceMeter()).intValue();
            foundPokemon.addExperienceAfterRunning(totalDistanceMeter);
        }
    }
}
