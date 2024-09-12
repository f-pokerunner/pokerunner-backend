package com.flab.pokerunner.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserPokemonDto {
    private String pokemonName;
    private String evolutionStatus;
    private int health;
    private int experience;
    private boolean defaultPokemon;
}
