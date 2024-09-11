package com.flab.pokerunner.domain.entity;

import com.flab.pokerunner.domain.enums.EvolutionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "evolution_status")
    private EvolutionStatus evolutionStatus;

    @Column(name = "image_url")
    private String imageUrl;

    @CreatedDate
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;
}
