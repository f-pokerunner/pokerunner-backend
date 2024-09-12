package com.flab.pokerunner.domain.dto.gu;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GuBossRankDto {
    public int ranking;
    public int userId;
    public String runningAddress;
    public String userNickname;
    public String totalDistanceMeter;
    public String pokemonName;
    public String imageUrl;
    public int exp;
    public int level;
}
