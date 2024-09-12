package com.flab.pokerunner.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

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

    @Column(name = "evolution_status")
    private String evolutionStatus;

    public int level;

    public int experience;


    @Column(name = "default_pokemon")
    public boolean defaultPokemon;

    @CreatedDate
    @Column(name = "created_at",updatable = false)
    public LocalDateTime createdAt;

    public void addExperienceAfterRunning(int distanceMeter) {
        experience += distanceMeter;
    }

    public void subtractHealth(int damage) {
        health -= damage;
    }
}
