package com.flab.pokerunner.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PokemonNotFoundException extends RuntimeException{
    private final String errorMessage;
}
