package com.flab.pokerunner.repository;

import com.flab.pokerunner.domain.entity.UserPokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPokemonRepository extends JpaRepository<UserPokemon, Long> {
}
