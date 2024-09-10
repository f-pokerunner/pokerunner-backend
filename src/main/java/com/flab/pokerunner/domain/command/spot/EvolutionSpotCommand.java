package com.flab.pokerunner.domain.command.spot;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EvolutionSpotCommand {
    public String address;
    public String pokemonName;
}
