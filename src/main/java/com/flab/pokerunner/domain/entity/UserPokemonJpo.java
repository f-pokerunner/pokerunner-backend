package com.flab.pokerunner.domain.entity;

import com.flab.pokerunner.domain.dto.PokemonLocationDto;
import com.flab.pokerunner.domain.event.running.PokemonSearched;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_pokemon")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserPokemonJpo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public int userId;
    public String userUuid;
    public String nickname;
    public int pokemonId;
    public int health;
    public int level;
    public int experience;
    @Column(name = "default_pokemon")
    public boolean defaultPokemon;
    @CreatedDate
    @Column(name = "created_at",updatable = false)
    public LocalDateTime createdAt;

    @Column(name = "evolution_status")
    private String evolutionStatus;

    public static UserPokemonJpo from(PokemonSearched event, PokemonLocationDto pokemonLocationDto) {
        return UserPokemonJpo.builder()
                .userId(event.getUserId())
                .pokemonId(pokemonLocationDto.getPokemonId())
                .nickname(pokemonLocationDto.getPokemonName())
                .health(100)
                .evolutionStatus(pokemonLocationDto.getEvolutionStatus())
                .level(1)
                .experience(0)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public void addExperienceAfterRunning(int distanceMeter) {
        experience += distanceMeter / 10;
    }

    public void subtractHealth(int damage) {
        health -= damage;
    }
}
