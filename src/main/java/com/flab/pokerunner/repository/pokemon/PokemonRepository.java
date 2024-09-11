package com.flab.pokerunner.repository.pokemon;

import com.flab.pokerunner.domain.entity.PokemonJpo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PokemonRepository extends JpaRepository<PokemonJpo, Integer> {
    PokemonJpo findByPokemonName(String pokemonName);
}
