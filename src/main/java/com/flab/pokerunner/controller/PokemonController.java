package com.flab.pokerunner.controller;

import com.flab.pokerunner.domain.dto.pokemon.DefaultPokemonDto;
import com.flab.pokerunner.service.pokemon.PokemonService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class PokemonController {

    private final PokemonService pokemonService;

    @GetMapping("/pokemon")
    public ResponseEntity<List<DefaultPokemonDto>> getDefaultPokemons() {
        List<DefaultPokemonDto> defaultPokemonDtos = pokemonService.getDefaultPokemons();
        return ResponseEntity.ok(defaultPokemonDtos);
    }

}
