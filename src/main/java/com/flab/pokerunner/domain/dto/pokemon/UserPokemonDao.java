package com.flab.pokerunner.domain.dto.pokemon;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserPokemonDao {
    public int userId;
    public int currentExp;
    public int evolutionStatus;
    public int level;
    public String pokemonName;
    public String imageUrl;
}
