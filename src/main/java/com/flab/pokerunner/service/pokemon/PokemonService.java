package com.flab.pokerunner.service.pokemon;

import com.flab.pokerunner.domain.dto.pokemon.DefaultPokemonDto;
import com.flab.pokerunner.domain.entity.PokemonJpo;
import com.flab.pokerunner.repository.pokemon.PokemonRepository;
import jakarta.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PokemonService {

    private final PokemonRepository pokemonRepository;

    public List<DefaultPokemonDto> getDefaultPokemons() {
        PokemonJpo picachu = pokemonRepository.findByPokemonNameAndEvolutionStatus("피카츄", "1");
        PokemonJpo bulbasaur = pokemonRepository.findByPokemonNameAndEvolutionStatus("이상해씨", "1");
        PokemonJpo ggobugi = pokemonRepository.findByPokemonNameAndEvolutionStatus("꼬부기", "1");

        return Arrays.asList(
                toDefaultPokemonDto(picachu),
                toDefaultPokemonDto(bulbasaur),
                toDefaultPokemonDto(ggobugi)
        );
    }

    private DefaultPokemonDto toDefaultPokemonDto(PokemonJpo pokemonJpo) {
        DefaultPokemonDto dto = new DefaultPokemonDto();
        dto.pokemonName = pokemonJpo.getPokemonName();
        dto.imageUrl = pokemonJpo.getImageUrl();
        return dto;
    }
}
