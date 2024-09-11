package com.flab.pokerunner.service.user;

import com.flab.pokerunner.domain.dto.UserSignUpRequestDTO;
import com.flab.pokerunner.domain.dto.UuidRequestDTO;
import com.flab.pokerunner.domain.entity.PokemonJpo;
import com.flab.pokerunner.domain.entity.UserJpo;
import com.flab.pokerunner.domain.entity.UserPokemonJpo;
import com.flab.pokerunner.repository.UserRepository;
import com.flab.pokerunner.repository.pokemon.PokemonRepository;
import com.flab.pokerunner.repository.pokemon.UserPokemonRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PokemonRepository pokemonRepository;
    private final UserPokemonRepository userPokemonRepository;

    public boolean checkUuid(UuidRequestDTO uuidRequestDTO) {
        return userRepository.existsByUuid(uuidRequestDTO.getUuid());
    }

    public boolean checkUserNicknameExists(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public boolean signup(UserSignUpRequestDTO userSignUpRequestDTO) {
        if (userRepository.existsByUuid(userSignUpRequestDTO.getUuid())) {
            log.info("UserJpo with UUID {} already exists.", userSignUpRequestDTO.getUuid());
            return false;
        }

        UserJpo userJpo = UserJpo.builder()
                .uuid(userSignUpRequestDTO.getUuid())
                .nickname(userSignUpRequestDTO.getNickname())
                .address(userSignUpRequestDTO.getAddress())
                .build();

        userRepository.save(userJpo);
        return true;
    }

    public void saveUserPokemon(UserSignUpRequestDTO userSignUpRequestDTO) {
        PokemonJpo pokemon = pokemonRepository.findByPokemonName(userSignUpRequestDTO.getPokemonName());

        UserPokemonJpo userPokemon = UserPokemonJpo.builder()
                .defaultPokemon(true)
                .pokemonId(pokemon.getId())
                .level(1)
                .health(100)
                .userUuid(userSignUpRequestDTO.getUuid())
                .evolutionStatus("STAGE_ONE")
                .experience(1)
                .build();

        userPokemonRepository.save(userPokemon);

    }
}
