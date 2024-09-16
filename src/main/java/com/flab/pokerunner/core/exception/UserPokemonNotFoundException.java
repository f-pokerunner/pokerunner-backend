package com.flab.pokerunner.core.exception;

public class UserPokemonNotFoundException extends RuntimeException {
    public UserPokemonNotFoundException(String message) {
        super(message);
    }
}
