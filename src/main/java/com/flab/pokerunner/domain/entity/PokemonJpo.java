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
