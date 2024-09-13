package com.flab.pokerunner.repository.pokemon;

import com.flab.pokerunner.domain.entity.UserPokemonJpo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserPokemonRepository extends JpaRepository<UserPokemonJpo, Integer> {

    UserPokemonJpo findByUserId(int userId);

    List<UserPokemonJpo> findAllByUserUuid(String userUuid);

    UserPokemonJpo findByUserIdAndPokemonId(int userId, int pokemonId);

    UserPokemonJpo findByUserUuidAndDefaultPokemon(String userUuid, boolean defaultPokemon);

    UserPokemonJpo findByUserUuidAndNickname(String userUuid, String nickname);

    UserPokemonJpo findFirstByUserUuidAndNickname(String userUuid, String nickname);

    Optional<UserPokemonJpo> findFirstByUserUuidAndDefaultPokemon(String userUuid, boolean defaultPokemon);

    List<UserPokemonJpo> findAllByUserId(int userId);
}
