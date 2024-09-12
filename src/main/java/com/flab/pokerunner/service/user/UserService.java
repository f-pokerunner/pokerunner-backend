package com.flab.pokerunner.service.user;

import com.flab.pokerunner.domain.dto.UserPokemonDto;
import com.flab.pokerunner.domain.dto.UserSignUpRequestDTO;
import com.flab.pokerunner.domain.dto.UuidRequestDTO;
import com.flab.pokerunner.domain.entity.PokemonJpo;
import com.flab.pokerunner.domain.entity.UserJpo;
import com.flab.pokerunner.domain.entity.UserPokemonJpo;

import com.flab.pokerunner.repository.UserRepository;

import com.flab.pokerunner.repository.pokemon.PokemonRepository;
import com.flab.pokerunner.repository.pokemon.UserPokemonRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

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
                .createdAt(LocalDateTime.now())
                .build();


        UserJpo savedUser = userRepository.save(userJpo);
        saveUserPokemon(userSignUpRequestDTO, savedUser.getId());
        return true;
    }


    private void saveUserPokemon(UserSignUpRequestDTO userSignUpRequestDTO, int userId) {
        PokemonJpo pokemon = pokemonRepository.findByPokemonName(userSignUpRequestDTO.getPokemonName());

        UserPokemonJpo userPokemon = UserPokemonJpo.builder()
                .defaultPokemon(true)
                .pokemonId(pokemon.getId())
                .nickname(pokemon.getPokemonName())
                .userId(userId)
                .level(1)
                .health(100)
                .userUuid(userSignUpRequestDTO.getUuid())
                .evolutionStatus("1")
                .experience(1)
                .createdAt(LocalDateTime.now())
                .build();

        userPokemonRepository.save(userPokemon);
    }

    public List<UserPokemonDto> getUserPokemons(String userUuid) {
        log.info("getting user {} pokemons...", userUuid);
        List<UserPokemonJpo> userPokemonJpos = userPokemonRepository.findAllByUserUuid(userUuid);
        return userPokemonJpos.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private UserPokemonDto convertToDto(UserPokemonJpo userPokemonJpo) {
        return UserPokemonDto.builder()
                .pokemonName(userPokemonJpo.getNickname())
                .evolutionStatus(userPokemonJpo.getEvolutionStatus())
                .health(userPokemonJpo.getHealth())
                .experience(userPokemonJpo.getExperience())
                .defaultPokemon(userPokemonJpo.isDefaultPokemon())
                .build();
    }
}
