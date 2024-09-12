package com.flab.pokerunner.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PokemonLocationDto {
    int id;
    String pokemonName;
    int pokemonId;
    String evolutionStatus;
    String imageUrl;
}
