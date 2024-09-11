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
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "user_pokemon")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPokemonJpo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public int userId;
    public String userUuid;
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
    public LocalDateTime createdDt;

    public void addExperienceAfterRunning(int distanceMeter) {
        experience += distanceMeter;
    }
}
