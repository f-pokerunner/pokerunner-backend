package com.flab.pokerunner.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_pokemon")
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

    public void addExperienceAfterRunning(int distanceMeter) {
        experience += distanceMeter / 10;
    }

    public void subtractHealth(int damage) {
        health -= damage;
    }
}
