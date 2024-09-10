package com.flab.pokerunner.domain.event.running;

import com.flab.pokerunner.domain.event.Events;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PokemonSearched extends Events {
    String lat;
    String lon;

    public static PokemonSearched of(double lat, double lon) {
        return new PokemonSearched(String.valueOf(lat), String.valueOf(lon));
    }
}
