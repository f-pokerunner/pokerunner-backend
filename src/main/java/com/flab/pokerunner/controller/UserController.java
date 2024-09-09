package com.flab.pokerunner.controller;

import com.flab.pokerunner.domain.dto.UserLoginRequestDTO;
import com.flab.pokerunner.domain.dto.UuidRequestDTO;
import com.flab.pokerunner.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/exists")
    public ResponseEntity<Boolean> checkExistingUser(@RequestBody UuidRequestDTO uuidRequestDTO) {
        boolean isExistingUser = userService.checkUuid(uuidRequestDTO);
        return ResponseEntity.ok(isExistingUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginRequestDTO userLoginRequestDTO) {
        boolean isnNewUser = userService.login(userLoginRequestDTO);
        if (isnNewUser) {
            return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("User logged in.", HttpStatus.OK);
        }
    }




}
