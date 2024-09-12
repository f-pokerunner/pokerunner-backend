package com.flab.pokerunner.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.flab.pokerunner.domain.dto.pokemon.UserPokemonDao;
import com.flab.pokerunner.domain.dto.running.UserRunningSummaryDto;

public record StopRunningResponse(
        @JsonProperty("pokemonInfo") PokemonInfo pokemonInfo,
        @JsonProperty("runningInfo") RunningInfo runningInfo) {

    public StopRunningResponse(PokemonInfo pokemonInfo, RunningInfo runningInfo) {
        this.pokemonInfo = pokemonInfo;
        this.runningInfo = runningInfo;
    }

    public static StopRunningResponse from(UserPokemonDao userPokemonDao, UserRunningSummaryDto userRunningSummaryDto) {
        return new StopRunningResponse(
                new PokemonInfo(
                        userPokemonDao.getUserId(),
                        userPokemonDao.getCurrentExp(),
                        userPokemonDao.getEvolutionStatus(),
                        userPokemonDao.getLevel(),
                        userPokemonDao.getPokemonName(),
                        userPokemonDao.getImageUrl()
                ),
                new RunningInfo(
                        userRunningSummaryDto.getTotalDistance(),
                        userRunningSummaryDto.getPace(),
                        userRunningSummaryDto.getAverageSpeed(),
                        userRunningSummaryDto.getDuration()
                )
        );
    }

    public record PokemonInfo(@JsonProperty("userId") int userId, @JsonProperty("currentExp") int currentExp,
                              @JsonProperty("evolutionStatus") int evolutionStatus, @JsonProperty("level") int level,
                              @JsonProperty("pokemonName") String pokemonName,
                              @JsonProperty("imageUrl") String imageUrl) {
        public PokemonInfo(int userId, int currentExp, int evolutionStatus, int level, String pokemonName, String imageUrl) {
            this.userId = userId;
            this.currentExp = currentExp;
            this.evolutionStatus = evolutionStatus;
            this.level = level;
            this.pokemonName = pokemonName;
            this.imageUrl = imageUrl;
        }
    }

    public record RunningInfo(@JsonProperty("totalDistance") double totalDistance, @JsonProperty("pace") String pace,
                              @JsonProperty("averageSpeed") double averageSpeed,
                              @JsonProperty("duration") long duration) {
        public RunningInfo(double totalDistance, String pace, double averageSpeed, long duration) {
            this.totalDistance = totalDistance;
            this.pace = pace;
            this.averageSpeed = averageSpeed;
            this.duration = duration;
        }
    }
}