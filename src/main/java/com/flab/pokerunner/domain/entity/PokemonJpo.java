package com.flab.pokerunner.domain.entity;

import com.flab.pokerunner.domain.enums.EvolutionStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "pokemon")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class PokemonJpo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "pokemon_name")
    private String pokemonName;

    @Column(name = "evolution_status")
    private String evolutionStatus;

    @Column(name = "image_url")
    private String imageUrl;

    @CreatedDate
    @Column(name = "created_dt", updatable = false)
    private LocalDateTime createdDt;
}
