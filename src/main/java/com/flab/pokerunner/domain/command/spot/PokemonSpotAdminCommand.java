package com.flab.pokerunner.domain.command.spot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PokemonSpotAdminCommand {
    public String address;
    public String name;
}
