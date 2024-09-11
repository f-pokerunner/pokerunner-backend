package com.flab.pokerunner.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UserRunningTimeDto {
    public int userId;
    public LocalDateTime earliestStartTime;
}
