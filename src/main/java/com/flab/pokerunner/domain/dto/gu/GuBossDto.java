package com.flab.pokerunner.domain.dto.gu;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuBossDto {
    int userId;
    String userNickname;
    String runningAddress;
    String totalDistance;
    String pokemonName;
    String imageUrl;
}
