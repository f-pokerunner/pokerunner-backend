package com.flab.pokerunner.domain.entity;

import com.flab.pokerunner.domain.enums.EvolutionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserPokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "pokemon_id", referencedColumnName = "id")
    private Pokemon pokemon;

    private Integer health;

    @Enumerated(EnumType.STRING)
    @Column(name = "evolution_status")
    private EvolutionStatus evolutionStatus;

    private Integer level;

    private Integer experience;

    @CreatedDate
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;
}
