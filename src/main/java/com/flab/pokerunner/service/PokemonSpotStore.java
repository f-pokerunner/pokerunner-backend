package com.flab.pokerunner.service;

import com.flab.pokerunner.domain.command.spot.PokemonSpotCommand;
import com.flab.pokerunner.domain.dto.PokemonLocationDto;
import com.flab.pokerunner.domain.dto.nhn.CoordinatesDto;
import com.flab.pokerunner.domain.dto.response.PokemonSpotDto;
import com.flab.pokerunner.domain.entity.PokemonJpo;
import com.flab.pokerunner.domain.event.running.PokemonSearched;
import com.flab.pokerunner.repository.PokemonLocJdbcRepository;
import com.flab.pokerunner.repository.pokemon.PokemonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PokemonSpotStore {

    private final NHNMapService nhnMapService;
    private final PokemonLocJdbcRepository pokemonLocJdbcRepository;
    private final PokemonRepository pokemonRepository;

    public PokemonSpotDto putPokemon(PokemonSpotCommand command) {
        CoordinatesDto dto = nhnMapService.getCoordinatesByAddress(command.getAddress());
        if (dto.getHeader().getResultCode() != 0) {
            return new PokemonSpotDto();
        }

        String lat = dto.getAddress().getAdm().get(0).getPosy();
        String lon = dto.getAddress().getAdm().get(0).getPosx();

        List<PokemonJpo> allPokemon = pokemonRepository.findAllByEvolutionStatus("1");
        List<Integer> pokemonIds = allPokemon.stream().map(PokemonJpo::getId).toList();

        Random random = new Random();
        int randomIndex = random.nextInt(pokemonIds.size());
        int randomPokemonId = pokemonIds.get(randomIndex);
        Optional<PokemonJpo> randomPokemon = pokemonRepository.findById(randomPokemonId);

        if (randomPokemon.isPresent()) {
            PokemonJpo selectedPokemon = randomPokemon.get();
            pokemonLocJdbcRepository.insertPokemonLocation(lat, lon, selectedPokemon.getPokemonName());
            return new PokemonSpotDto(lat, lon, command.getAddress(), selectedPokemon.getPokemonName());
        } else {
            return new PokemonSpotDto(lat, lon, command.getAddress(), "Unknown Pokemon");
        }
    }

    public List<PokemonLocationDto> searchPokemon(PokemonSearched event) {
        List<PokemonLocationDto> pokemonLocationsNearby = pokemonLocJdbcRepository.findPokemonLocationsNearby(event.getLon(), event.getLat(), "5000");
        pokemonLocationsNearby.forEach(it -> {
            pokemonLocJdbcRepository.updateOwnerId(it.getId(), event.getUserId());
        });
        return pokemonLocationsNearby;
    }
}
