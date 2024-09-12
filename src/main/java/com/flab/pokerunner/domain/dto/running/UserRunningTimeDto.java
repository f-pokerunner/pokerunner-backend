package com.flab.pokerunner.domain.dto.running;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UserRunningTimeDto {
    public int userId;
    public int defaultPokemonId;
    public LocalDateTime earliestStartTime;
}
