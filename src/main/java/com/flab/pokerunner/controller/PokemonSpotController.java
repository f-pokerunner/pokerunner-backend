package com.flab.pokerunner.controller;

import com.flab.pokerunner.domain.command.spot.PokemonSpotCommand;
import com.flab.pokerunner.domain.dto.response.PokemonSpotDto;
import com.flab.pokerunner.service.PokemonSpotStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemon-spot")
@RequiredArgsConstructor
public class PokemonSpotController {

    private final PokemonSpotStore pokemonSpotStore;

    @PostMapping
    public ResponseEntity<PokemonSpotDto> createEvolutionSpot(@RequestBody PokemonSpotCommand command) {
        PokemonSpotDto pokemonSpotDto = pokemonSpotStore.putPokemon(command);
        return ResponseEntity.ok(pokemonSpotDto);
    }
}
