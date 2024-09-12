package com.flab.pokerunner.domain.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class UserPokemonJpoTest {

    private UserPokemonJpo userPokemonJpo;
    private PokemonJpo pokemonJpo;

    @BeforeEach
    public void setUp() {
        userPokemonJpo = UserPokemonJpo.builder()
                .id(1)
                .userUuid(null)
                .nickname("꼬부기")
                .evolutionStatus("1")
                .pokemonId(7)
                .health(30)
                .level(1)
                .experience(0)
                .defaultPokemon(true)
                .createdAt(LocalDateTime.now())
                .build();

        pokemonJpo = PokemonJpo.builder()
                .id(7)
                .pokemonName("꼬부기")
                .evolutionStatus("1")
                .imageUrl("")
                .createdDt(LocalDateTime.now())
                .preEvolutionName(null)
                .nextEvolutionName("어니부기")
                .build();
    }

    @Test
    @DisplayName("달린 거리에 따른 경험치 획득 테스트")
    public void getExpTest() {
        userPokemonJpo.addExperienceAfterRunning(1500, pokemonJpo); // 경험치 150

        assertThat(userPokemonJpo.getExperience()).isEqualTo(50);
        assertThat(userPokemonJpo.getLevel()).isEqualTo(2);

        userPokemonJpo.addExperienceAfterRunning(500, pokemonJpo); // 경험치 50 추가

        assertThat(userPokemonJpo.getExperience()).isEqualTo(0);
        assertThat(userPokemonJpo.getLevel()).isEqualTo(3);
        assertThat(userPokemonJpo.getNickname()).isEqualTo("어니부기");
    }
}
