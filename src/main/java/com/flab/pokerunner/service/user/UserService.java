package com.flab.pokerunner.service.user;

import com.flab.pokerunner.domain.dto.*;
import com.flab.pokerunner.domain.dto.running.UserRunningInfoDto;
import com.flab.pokerunner.domain.entity.PokemonJpo;
import com.flab.pokerunner.domain.entity.UserJpo;
import com.flab.pokerunner.domain.entity.UserPokemonJpo;
import com.flab.pokerunner.domain.entity.UserRunningJpo;
import com.flab.pokerunner.exceptions.PokemonNotFoundException;
import com.flab.pokerunner.repository.UserRepository;
import com.flab.pokerunner.repository.pokemon.PokemonRepository;
import com.flab.pokerunner.repository.pokemon.UserPokemonRepository;
import com.flab.pokerunner.repository.running.UserRunningRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PokemonRepository pokemonRepository;
    private final UserPokemonRepository userPokemonRepository;
    private final UserRunningRepository userRunningRepository;

    public boolean checkUuid(UuidRequestDTO uuidRequestDTO) {
        return userRepository.existsByUuid(uuidRequestDTO.getUuid());
    }

    public boolean checkUserNicknameExists(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public int signup(UserSignUpRequestDTO userSignUpRequestDTO) {
        if (userRepository.existsByUuid(userSignUpRequestDTO.getUuid())) {
            log.info("UserJpo with UUID {} already exists.", userSignUpRequestDTO.getUuid());
            UserJpo user = userRepository.findByUuid(userSignUpRequestDTO.getUuid());
            return user.getId();
        }


        UserJpo userJpo = UserJpo.builder()
                .uuid(userSignUpRequestDTO.getUuid())
                .nickname(userSignUpRequestDTO.getNickname())
                .address(userSignUpRequestDTO.getAddress())
                .createdAt(LocalDateTime.now())
                .build();


        UserJpo savedUser = userRepository.save(userJpo);
        saveUserPokemon(userSignUpRequestDTO, savedUser.getId());
        return savedUser.getId();
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
        Optional<PokemonJpo> pokemon = pokemonRepository.findById(userPokemonJpo.getPokemonId());
        String imageUrl = pokemon.map(PokemonJpo::getImageUrl)
                .orElse("");
        return UserPokemonDto.builder()
                .pokemonName(userPokemonJpo.getNickname())
                .evolutionStatus(userPokemonJpo.getEvolutionStatus())
                .imageUrl(imageUrl)
                .health(userPokemonJpo.getHealth())
                .experience(userPokemonJpo.getExperience())
                .defaultPokemon(userPokemonJpo.isDefaultPokemon())
                .build();
    }

    public boolean setDefaultPokemon(UserSetDefaultPokemonDto userSetDefaultPokemonDto) {
        UserPokemonJpo currentDefaultPokemon = userPokemonRepository.findByUserUuidAndDefaultPokemon(userSetDefaultPokemonDto.getUuid(), true);
        if (currentDefaultPokemon != null) {
            currentDefaultPokemon.setDefaultPokemon(false);
            userPokemonRepository.save(currentDefaultPokemon);
        }

        UserPokemonJpo newDefaultPokemon = userPokemonRepository.findFirstByUserUuidAndNickname(userSetDefaultPokemonDto.getUuid(), userSetDefaultPokemonDto.getPokemonName());
        if (newDefaultPokemon == null) {
            log.info("Requested Pokémon not found");
            return false;
        }
        newDefaultPokemon.setDefaultPokemon(true);
        userPokemonRepository.save(newDefaultPokemon);

        //update Pokemon Repo
        UserJpo userJpo = userRepository.findByUuid(userSetDefaultPokemonDto.getUuid());
        userJpo.setDefaultPokemonId(newDefaultPokemon.getPokemonId());
        userRepository.save(userJpo);
        return true;
    }

    public void addUserPokemon(AddPokemonDto addPokemonDto) {
        UserJpo user = userRepository.findByUuid(addPokemonDto.getUuid());
        PokemonJpo pokemon = pokemonRepository.findByPokemonName(addPokemonDto.getPokemonName());
        UserPokemonJpo newUserPokemon = UserPokemonJpo.builder()
                .defaultPokemon(false)
                .nickname(pokemon.getPokemonName())
                .pokemonId(pokemon.getId())
                .health(100)
                .experience(1)
                .level(1)
                .userId(user.getId())
                .userUuid(user.getUuid())
                .evolutionStatus("1")
                .createdAt(LocalDateTime.now())
                .build();
        userPokemonRepository.save(newUserPokemon);
    }

    public List<UserRunningInfoDto> getUserRunningInfo(String userUuid) {
        UserJpo user = userRepository.findByUuid(userUuid);
        List<UserRunningJpo> userRunningJpos = userRunningRepository.findAllByUserId(user.getId());

        return userRunningJpos.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private UserRunningInfoDto convertToDto(UserRunningJpo userRunningJpo) {
        return UserRunningInfoDto.builder()
                .distanceMeter(userRunningJpo.getDistanceMeter())
                .pace(userRunningJpo.getPace())
                .startTime(userRunningJpo.getStartTime())
                .endTime(userRunningJpo.getEndTime())
                .guAddress(userRunningJpo.getGuAddress())
                .build();
    }

    public UserHomeDto getUserHomeInfo(String userUuid) {
        UserJpo userJpo = userRepository.findByUuid(userUuid);
        UserPokemonJpo userPokemonJpo = userPokemonRepository.findFirstByUserUuidAndDefaultPokemon(userJpo.getUuid(), true)
                .orElseThrow(() -> new PokemonNotFoundException("Default Pokemon not found for user: " + userJpo.getUuid()));

        PokemonJpo pokemonJpo = pokemonRepository.findByPokemonNameAndEvolutionStatus(userPokemonJpo.getNickname(), userPokemonJpo.getEvolutionStatus());
        LocalDateTime latestRunEndTime = userRunningRepository.findLatestEndTimeByUserId(userJpo.getId());

        int notRunningDays = 0;
        if (latestRunEndTime != null) {
            notRunningDays = (int) ChronoUnit.DAYS.between(latestRunEndTime.toLocalDate(), LocalDate.now());
        }

        List<String> comments = new ArrayList<>();
        comments.add("오늘도 달려달려~ 피카츄처럼 빠르게!");
        comments.add("조금 더 달리면 진화할 수 있을 거야! 끝까지 가자!");
        comments.add("달리면서 체력을 키워, 너의 포켓몬도 강해질 거야!");
        comments.add("매일매일 달리기! 파이리의 불꽃처럼 포기하지 말자!");
        comments.add("나와 함께 뛰면 넌 더 빨라질 거야!");
        comments.add("달리는 순간마다 너의 포켓몬도 함께 성장해!");
        comments.add("레이스가 끝날 때까지 힘내! 우리는 이길 수 있어!");
        comments.add("비록 천천히 달려도 괜찮아, 꾸준함이 강함이야!");
        comments.add("포켓몬이 널 응원해! 달리자, 오늘도 최고의 트레이너가 되어보자!");

        Random random = new Random();
        String randomComment = comments.get(random.nextInt(comments.size()));


        return UserHomeDto.builder()
            .level(userPokemonJpo.getLevel())
            .experience(userPokemonJpo.getExperience())
            .imageUrl(pokemonJpo.getImageUrl())
            .pokemonName(userPokemonJpo.getNickname())
            .userNickname(userJpo.getNickname())
            .notRunningDays(notRunningDays)
            .pokemonComment(randomComment)
            .build();
    }

}
