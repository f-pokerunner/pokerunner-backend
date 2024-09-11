package com.flab.pokerunner.domain.event.running;

import com.flab.pokerunner.domain.event.Events;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PokemonSearched extends Events {
    int userId;
    String lat;
    String lon;

    public static PokemonSearched of(int userId, double lat, double lon) {
        return new PokemonSearched(userId, String.valueOf(lat), String.valueOf(lon));
    }
}
