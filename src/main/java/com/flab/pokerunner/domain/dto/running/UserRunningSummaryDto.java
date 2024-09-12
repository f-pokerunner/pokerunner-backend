package com.flab.pokerunner.domain.dto.running;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserRunningSummaryDto {
    private double totalDistance;
    private String pace;
    private double averageSpeed;
    private long duration;
}
