package com.flab.pokerunner.repository.pokemon;

import com.flab.pokerunner.domain.entity.PokemonJpo;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PokemonRepository extends JpaRepository<PokemonJpo, Integer> {

    Optional<PokemonJpo> findById(int pokemonId);
    PokemonJpo findByPokemonName(String pokemonName);

    PokemonJpo findByPokemonNameAndEvolutionStatus(String pokemonName, String evolutionStatus);

    List<PokemonJpo> findAllByEvolutionStatus(String evolutionStatus);

}
