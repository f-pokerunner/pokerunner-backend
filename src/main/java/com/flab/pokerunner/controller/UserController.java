package com.flab.pokerunner.controller;

import com.flab.pokerunner.domain.dto.UserSignUpRequestDTO;
import com.flab.pokerunner.domain.dto.UuidRequestDTO;
import com.flab.pokerunner.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/checkUserNicknameExist/{user_nickname}")
    public ResponseEntity<Boolean> checkUserNicknameExists(@PathVariable String user_nickname) {
        boolean userNicknameExists = userService.checkUserNicknameExists(user_nickname);
        return ResponseEntity.ok(userNicknameExists);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignUpRequestDTO userSignUpRequestDTO) {
        boolean isnNewUser = userService.signup(userSignUpRequestDTO);
        userService.saveUserPokemon(userSignUpRequestDTO);
        if (isnNewUser) {
            return new ResponseEntity<>("UserJpo created successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("UserJpo logged in.", HttpStatus.OK);
        }
    }
}
