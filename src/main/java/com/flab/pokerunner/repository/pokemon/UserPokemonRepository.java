package com.flab.pokerunner.repository.pokemon;

import com.flab.pokerunner.domain.entity.UserPokemonJpo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPokemonRepository extends JpaRepository<UserPokemonJpo, Integer> {
    UserPokemonJpo findByUserId(int userId);
}
