package com.flab.pokerunner.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserHomeDto {
    private int level;
    private int experience;
    private String pokemonName;
    private String imageUrl;
    private String userNickname;
    private int notRunningDays;
}
