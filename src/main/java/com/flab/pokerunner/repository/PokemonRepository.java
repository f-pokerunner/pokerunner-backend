package com.flab.pokerunner.repository;

import com.flab.pokerunner.domain.entity.Pokemon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
}
