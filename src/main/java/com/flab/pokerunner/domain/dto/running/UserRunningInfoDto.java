package com.flab.pokerunner.domain.dto.running;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class UserRunningInfoDto {
    String distanceMeter;
    String pace;
    LocalDateTime startTime;
    LocalDateTime endTime;
    String guAddress;
}
