package com.flab.pokerunner.domain.event.running;

import com.flab.pokerunner.domain.event.Events;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
@Getter
public class RunningStarted extends Events {
    int userId;
    LocalDateTime startTime;
}
