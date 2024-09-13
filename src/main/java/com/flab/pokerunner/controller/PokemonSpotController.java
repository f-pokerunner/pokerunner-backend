package com.flab.pokerunner.controller;

import com.flab.pokerunner.domain.command.spot.PokemonSpotAdminCommand;
import com.flab.pokerunner.domain.command.spot.PokemonSpotCommand;
import com.flab.pokerunner.domain.dto.response.PokemonSpotDto;
import com.flab.pokerunner.service.PokemonSpotStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/pokemon-spot")
@RequiredArgsConstructor
@RestController
public class PokemonSpotController {

    private final PokemonSpotStore pokemonSpotStore;

    @PostMapping("/place")
    public ResponseEntity<PokemonSpotDto> test(@RequestBody PokemonSpotCommand command) {
        PokemonSpotDto pokemonSpotDto = pokemonSpotStore.putPokemon(command);
        return ResponseEntity.ok(pokemonSpotDto);
    }

    @PostMapping("/place-admin")
    public ResponseEntity<PokemonSpotDto> testAdmin(@RequestBody PokemonSpotAdminCommand command) {
        PokemonSpotDto pokemonSpotDto = pokemonSpotStore.putPokemon(command);
        return ResponseEntity.ok(pokemonSpotDto);
    }
}
