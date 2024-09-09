package com.flab.pokerunner.domain.event.running;

import com.flab.pokerunner.domain.event.Events;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@ToString
public class RunningStopped extends Events {
    int userId;
    Double totalDistanceMeter;
    Double averageSpeed;
    String pace;
    LocalDateTime endTime;
}
