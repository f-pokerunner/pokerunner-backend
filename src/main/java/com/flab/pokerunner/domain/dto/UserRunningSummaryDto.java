package com.flab.pokerunner.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRunningSummaryDto {
    private int userId;
    private String guAddress;
    private String totalDistance;
}
