package com.flab.pokerunner.service;

import com.flab.pokerunner.domain.command.spot.EvolutionSpotCommand;
import com.flab.pokerunner.domain.dto.PokemonLocationDto;
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

    public List<PokemonLocationDto> searchPokemon(PokemonSearched event) {
        List<PokemonLocationDto> pokemonLocationsNearby = pokemonLocJdbcRepository.findPokemonLocationsNearby(event.getLon(), event.getLat(), "5000");
        pokemonLocationsNearby.forEach(it -> {
            pokemonLocJdbcRepository.updateOwnerId(it.getId(), event.getUserId());
        });
        return pokemonLocationsNearby;
    }
}
