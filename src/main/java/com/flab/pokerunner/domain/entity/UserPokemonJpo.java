package com.flab.pokerunner.domain.entity;

import com.flab.pokerunner.domain.dto.PokemonLocationDto;
import com.flab.pokerunner.domain.event.running.PokemonSearched;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_pokemon")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPokemonJpo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public int userId;
    public int pokemonId;
    public String nickname;
    public int health;
    public String evolutionStatus;
    public int level;
    public int experience;
    public LocalDateTime createdDt;

    public static UserPokemonJpo from(PokemonSearched event, PokemonLocationDto pokemonLocationDto) {
        return UserPokemonJpo.builder()
                .userId(event.getUserId())
                .pokemonId(pokemonLocationDto.getPokemonId())
                .nickname(pokemonLocationDto.getPokemonName())
                .health(100)
                .evolutionStatus(pokemonLocationDto.getEvolutionStatus())
                .level(1)
                .experience(0)
                .createdDt(LocalDateTime.now())
                .build();
    }

    public void addExperienceAfterRunning(int distanceMeter) {
        experience += distanceMeter / 10;
    }

    public void subtractHealth(int damage) {
        health -= damage;
    }
}
