package com.flab.pokerunner.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AddPokemonDto {
    String uuid;
    String pokemonName;
}
