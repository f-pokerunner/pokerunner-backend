package com.flab.pokerunner.controller;

import com.flab.pokerunner.domain.command.spot.EvolutionSpotCommand;
import com.flab.pokerunner.service.PokemonSpotStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemon-spots")
@RequiredArgsConstructor
public class EvolutionSpotController {

    private final PokemonSpotStore pokemonSpotStore;

    @PostMapping
    public ResponseEntity<String> createEvolutionSpot(@RequestBody EvolutionSpotCommand command) {
        String res = pokemonSpotStore.putPokemon(command);
        return ResponseEntity.ok(res);
    }
}
