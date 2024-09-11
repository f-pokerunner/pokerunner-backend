package com.flab.pokerunner.service.pokemon;

import com.flab.pokerunner.domain.dto.PokemonLocationDto;
import com.flab.pokerunner.domain.entity.User;
import com.flab.pokerunner.domain.entity.UserPokemonJpo;
import com.flab.pokerunner.domain.event.running.PokemonSearched;
import com.flab.pokerunner.domain.event.running.RunningStopped;
import com.flab.pokerunner.repository.UserRepository;
import com.flab.pokerunner.repository.pokemon.UserPokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PokemonStore {

    private final UserRepository userRepository;
    private final UserPokemonRepository userPokemonRepository;

    @Transactional
    public void save(RunningStopped event) {
        User user = userRepository.findById(event.getUserId());
        Integer defaultPokemonId = user.getDefaultPokemonId();
        if (defaultPokemonId == null) return;

        UserPokemonJpo foundPokemon = userPokemonRepository.findByUserIdAndPokemonId(event.getUserId(), defaultPokemonId);

        if (foundPokemon != null) {
            int totalDistanceMeter = Double.valueOf(event.getTotalDistanceMeter()).intValue();
            foundPokemon.addExperienceAfterRunning(totalDistanceMeter);
        }
    }

    @Transactional
    public void save(PokemonSearched event, PokemonLocationDto pokemonName) {
        UserPokemonJpo capturedPokemon = UserPokemonJpo.from(event, pokemonName);
        userPokemonRepository.save(capturedPokemon);
    }
}
