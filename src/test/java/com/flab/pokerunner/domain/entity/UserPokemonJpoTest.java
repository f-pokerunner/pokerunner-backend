package com.flab.pokerunner.domain.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class UserPokemonJpoTest {

    @Test
    void addExperienceAfterRunning_LevelUpCase() {
        // Given
        UserPokemonJpo userPokemonJpo = UserPokemonJpo.builder()
                .userId(1)
                .pokemonId(1)
                .nickname("Pikachu")
                .health(100)
                .evolutionStatus("0")
                .level(1)
                .experience(0)
                .createdDt(LocalDateTime.now())
                .build();

        // When
        userPokemonJpo.addExperienceAfterRunning(1000);

        // Then
        Assertions.assertEquals(2, userPokemonJpo.level);
        Assertions.assertEquals(0, userPokemonJpo.experience);
    }

    @Test
    void addExperienceAfterRunning_EvolutionCase() {
        // Given
        UserPokemonJpo userPokemonJpo = UserPokemonJpo.builder()
                .userId(1)
                .pokemonId(1)
                .nickname("Pikachu")
                .health(100)
                .evolutionStatus("0")
                .level(1)
                .experience(0)
                .createdDt(LocalDateTime.now())
                .build();

        // When
        userPokemonJpo.addExperienceAfterRunning(3000);

        // Then
        Assertions.assertEquals(4, userPokemonJpo.level);
        Assertions.assertEquals("1", userPokemonJpo.evolutionStatus);
    }

    @Test
    void addExperienceAfterRunning_SmallDistanceCase() {
        // Given
        UserPokemonJpo userPokemonJpo = UserPokemonJpo.builder()
                .userId(1)
                .pokemonId(1)
                .nickname("Pikachu")
                .health(100)
                .evolutionStatus("0")
                .level(1)
                .experience(0)
                .createdDt(LocalDateTime.now())
                .build();

        // When
        userPokemonJpo.addExperienceAfterRunning(500);

        // Then
        Assertions.assertEquals(1, userPokemonJpo.level);
        Assertions.assertEquals(50, userPokemonJpo.experience);
    }

    @Test
    void addExperienceAfterRunning_EvolutionAndRemainingExperienceCase() {
        // Given
        UserPokemonJpo userPokemonJpo = UserPokemonJpo.builder()
                .userId(1)
                .pokemonId(1)
                .nickname("Pikachu")
                .health(100)
                .evolutionStatus("1")
                .level(1)
                .experience(0)
                .createdDt(LocalDateTime.now())
                .build();

        // When
        userPokemonJpo.addExperienceAfterRunning(1300); // exp 130

        // Then
        Assertions.assertEquals(2, userPokemonJpo.level);
        Assertions.assertEquals(30, userPokemonJpo.experience);
        Assertions.assertEquals("1", userPokemonJpo.evolutionStatus);

        // further evolution check
        userPokemonJpo.addExperienceAfterRunning(1300); // exp 130
        Assertions.assertEquals(3, userPokemonJpo.level);
        Assertions.assertEquals(60, userPokemonJpo.experience);
        Assertions.assertEquals("2", userPokemonJpo.evolutionStatus);
    }
}