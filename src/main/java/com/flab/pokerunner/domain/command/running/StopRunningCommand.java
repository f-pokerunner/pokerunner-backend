package com.flab.pokerunner.domain.command.running;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StopRunningCommand {
    public int userId;
}
