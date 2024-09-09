package com.flab.pokerunner.domain.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequestDTO {
    private String uuid;
    private String nickname;
    private String address;
}
