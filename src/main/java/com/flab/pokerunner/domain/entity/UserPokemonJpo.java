package com.flab.pokerunner.domain.entity;

import com.flab.pokerunner.domain.dto.PokemonLocationDto;
import com.flab.pokerunner.domain.event.running.PokemonSearched;
import jakarta.persistence.*;
import lombok.*;
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
    @Column(name = "created_at", updatable = false)
    public LocalDateTime createdAt;

    @Column(name = "evolution_status")
    public String evolutionStatus;

    public static UserPokemonJpo from(PokemonSearched event, PokemonLocationDto pokemonLocationDto, String uuid) {
        return UserPokemonJpo.builder()
                .userId(event.getUserId())
                .pokemonId(pokemonLocationDto.getPokemonId())
                .nickname(pokemonLocationDto.getPokemonName())
                .health(100)
                .evolutionStatus(pokemonLocationDto.getEvolutionStatus())
                .level(1)
                .experience(0)
                .createdAt(LocalDateTime.now())
                .userUuid(uuid)
                .build();
    }

    /**
     * 주어진 거리(m)를 달린 후 포켓몬에게 경험치를 추가합니다.
     *
     * @param distanceMeter 이동한 거리, 단위는 미터
     * @param pokemonJpo    경험치를 받을 포켓몬 정보 객체
     */
    public void addExperienceAfterRunning(int distanceMeter, PokemonJpo pokemonJpo) {
        int remainingExperience = distanceMeter / 10;

        while (remainingExperience > 0) {
            remainingExperience = addExperience(remainingExperience, pokemonJpo);
        }
    }

    /**
     * 포켓몬에게 경험치를 추가하고 이를 바탕으로 레벨업을 처리합니다.
     *
     * @param remainingExperience 남은 경험치 포인트
     * @param pokemonJpo          포켓몬 정보 객체
     * @return 남은 경험치 포인트
     */
    private int addExperience(int remainingExperience, PokemonJpo pokemonJpo) {
        int requiredExperienceForNextLevel = 100 - this.experience;

        if (remainingExperience >= requiredExperienceForNextLevel) {
            remainingExperience -= requiredExperienceForNextLevel;
            this.level++;
            this.experience = 0;
            checkEvolution(pokemonJpo);
        } else {
            this.experience += remainingExperience;
            remainingExperience = 0;
        }

        return remainingExperience;
    }

    private void checkEvolution(PokemonJpo pokemonJpo) {
        if (checkFirstStageEvolution()) {
            this.nickname = pokemonJpo.getNextEvolutionName();
        } else if (checkSecondStageEvolution()) {
            this.nickname = pokemonJpo.getNextEvolutionName();
        }
    }

    /**
     * 첫번째 진화 조건을 만족하면 evolutionStatus = 2, 포켓몬 이름을 진화체 다음 단계로 설정
     */
    private boolean checkFirstStageEvolution() {
        if ("1".equals(this.evolutionStatus) && this.level >= 3) {
            this.evolutionStatus = "2";
            return true;
        }
        return false;
    }

    /**
     * 두번째 진화 조건을 만족하면 evolutionStatus = 3, 포켓몬 이름을 진화체 다음 단계로 설정
     */
    private boolean checkSecondStageEvolution() {
        if ("2".equals(this.evolutionStatus) && this.level >= 5) {
            this.evolutionStatus = "3";
            return true;
        }
        return false;
    }


    public void subtractHealth(int damage) {
        health -= damage;
    }
}
