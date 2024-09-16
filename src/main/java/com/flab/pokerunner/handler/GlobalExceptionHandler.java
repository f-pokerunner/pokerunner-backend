package com.flab.pokerunner.handler;

import com.flab.pokerunner.core.exception.UserPokemonNotFoundException;
import com.flab.pokerunner.domain.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UserPokemonNotFoundException.class)
    public ResponseEntity<CommonResponse> handleException(UserPokemonNotFoundException e) {
        CommonResponse response = new CommonResponse("0001", "기본 포켓몬을 찾을 수 없습니다");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
