package com.flab.pokerunner.domain.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
