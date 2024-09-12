package com.flab.pokerunner.domain.dto;

import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public class UserRunningInfoDto {
    int distanceMeter;
    String pace;
    LocalDateTime startTime;
    LocalDateTime endTime;
}
