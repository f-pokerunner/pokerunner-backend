package com.flab.pokerunner.domain.dto.response;

import com.flab.pokerunner.domain.dto.pokemon.UserPokemonDao;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StopRunningResponse {
    public int userId;
    public int currentExp;
    public int evolutionStatus;

    public static StopRunningResponse of(UserPokemonDao userPokemonDao) {
        return new StopRunningResponse(
                userPokemonDao.getUserId(),
                userPokemonDao.getCurrentExp(),
                userPokemonDao.getEvolutionStatus()
        );
    }
}
