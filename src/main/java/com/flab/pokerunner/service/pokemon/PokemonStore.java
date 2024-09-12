package com.flab.pokerunner.service.pokemon;

import com.flab.pokerunner.domain.dto.PokemonLocationDto;
import com.flab.pokerunner.domain.entity.UserJpo;
import com.flab.pokerunner.domain.entity.UserPokemonJpo;
import com.flab.pokerunner.domain.event.running.PokemonSearched;
import com.flab.pokerunner.domain.event.running.RunningStopped;
import com.flab.pokerunner.repository.UserRepository;
import com.flab.pokerunner.repository.pokemon.UserPokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PokemonStore {

    private final UserRepository userRepository;
    private final UserPokemonRepository userPokemonRepository;
    private final Environment env;

    @Transactional
    public void save(RunningStopped event) {
        UserJpo user = userRepository.findById(event.getUserId());
        Integer defaultPokemonId = user.getDefaultPokemonId();
        if (defaultPokemonId == null) return;

        UserPokemonJpo foundPokemon = userPokemonRepository.findByUserIdAndPokemonId(event.getUserId(), defaultPokemonId);

        if (foundPokemon != null) {
            int totalDistanceMeter = event.getTotalDistanceMeter().intValue();
            foundPokemon.addExperienceAfterRunning(totalDistanceMeter);
        }
    }

    @Transactional
    public void save(PokemonSearched event, PokemonLocationDto pokemonName) {
        UserPokemonJpo capturedPokemon = UserPokemonJpo.from(event, pokemonName);
        userPokemonRepository.save(capturedPokemon);
    }
}
