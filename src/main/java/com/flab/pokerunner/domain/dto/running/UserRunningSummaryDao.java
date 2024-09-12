package com.flab.pokerunner.domain.dto.running;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRunningSummaryDao {
    private int userId;
    private String guAddress;
    private String totalDistance;
}
