package com.flab.pokerunner.service.pokemon;

import com.flab.pokerunner.domain.dto.PokemonLocationDto;
import com.flab.pokerunner.domain.entity.PokemonJpo;
import com.flab.pokerunner.domain.entity.UserJpo;
import com.flab.pokerunner.domain.entity.UserPokemonJpo;
import com.flab.pokerunner.domain.event.running.PokemonSearched;
import com.flab.pokerunner.domain.event.running.RunningStopped;
import com.flab.pokerunner.repository.UserRepository;
import com.flab.pokerunner.repository.pokemon.PokemonRepository;
import com.flab.pokerunner.repository.pokemon.UserPokemonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PokemonStore {

    private final UserRepository userRepository;
    private final UserPokemonRepository userPokemonRepository;
    private final PokemonRepository pokemonRepository;

    @Transactional
    public void save(RunningStopped event) {
        UserJpo user = userRepository.findById(event.getUserId());
        Integer defaultPokemonId = user.getDefaultPokemonId();
        if (defaultPokemonId == null) return;

        UserPokemonJpo foundPokemon = userPokemonRepository.findByUserIdAndPokemonId(event.getUserId(), defaultPokemonId);

        if (foundPokemon != null) {
            PokemonJpo pokemonJpo = pokemonRepository.findByPokemonName(foundPokemon.getNickname());
            int totalDistanceMeter = event.getTotalDistanceMeter().intValue();
            foundPokemon.addExperienceAfterRunning(totalDistanceMeter, pokemonJpo);

            pokemonJpo = pokemonRepository.findByPokemonName(foundPokemon.getNickname());

            foundPokemon.pokemonId = pokemonJpo.getId();
            user.setDefaultPokemonId(foundPokemon.pokemonId);
        }
    }

    @Transactional
    public void save(PokemonSearched event, PokemonLocationDto pokemonName) {
        UserJpo foundUser = userRepository.findById(event.getUserId());
        String uuid = foundUser.getUuid();
        UserPokemonJpo capturedPokemon = UserPokemonJpo.from(event, pokemonName, uuid);
        userPokemonRepository.save(capturedPokemon);
    }

    @Transactional(readOnly = true)
    public boolean duplicateSamePokemon(int userId, String foundPokemonName) {
        List<UserPokemonJpo> foundPokemons = userPokemonRepository.findAllByUserId(userId);
        List<String> nicknames = foundPokemons.stream().map(UserPokemonJpo::getNickname).toList();

        for (String nickname : nicknames) {
            if (nickname.equals(foundPokemonName)) {
                log.info("{} 은 {} 을 이미 가지고 있습니다.", userId, foundPokemonName);
                return true;
            }
        }
        return false;
    }
}
