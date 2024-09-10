package com.flab.pokerunner.service;

import com.flab.pokerunner.domain.command.spot.EvolutionSpotCommand;
import com.flab.pokerunner.domain.dto.nhn.CoordinatesDto;
import com.flab.pokerunner.domain.event.running.PokemonSearched;
import com.flab.pokerunner.repository.PokemonLocJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PokemonSpotStore {

    private final NHNMapService nhnMapService;
    private final PokemonLocJdbcRepository pokemonLocJdbcRepository;

    public String putPokemon(EvolutionSpotCommand command) {
        CoordinatesDto dto = nhnMapService.getCoordinatesByAddress(command.getAddress());
        if (dto.getHeader().getResultCode() != 0) {
            return "Fail";
        }

        String lat = dto.getAddress().getAdm().get(0).getPosy();
        String lon = dto.getAddress().getAdm().get(0).getPosx();

        pokemonLocJdbcRepository.insertPokemonLocation(lat, lon, command.getPokemonName());
        return "Success";
    }

    public List<String> searchPokemon(PokemonSearched event) {
        List<String> foundPokemon = pokemonLocJdbcRepository.findPokemonLocationsNearby(event.getLon(), event.getLat(), "55000");
        return foundPokemon;
    }
}
