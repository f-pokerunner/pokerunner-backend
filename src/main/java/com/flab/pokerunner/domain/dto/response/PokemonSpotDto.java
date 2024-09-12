package com.flab.pokerunner.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class PokemonSpotDto {
    String lat;
    String lng;
    String address;
    String pokemonName;
}
