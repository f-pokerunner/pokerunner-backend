package com.flab.pokerunner.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PokemonSpotDto {
    String lat;
    String lng;
    String address;
    String pokemonName;
}
