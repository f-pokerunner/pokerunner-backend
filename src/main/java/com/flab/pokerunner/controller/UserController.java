package com.flab.pokerunner.controller;

import com.flab.pokerunner.domain.dto.AddPokemonDto;
import com.flab.pokerunner.domain.dto.UserPokemonDto;
import com.flab.pokerunner.domain.dto.UserRunningInfoDto;
import com.flab.pokerunner.domain.dto.UserSetDefaultPokemonDto;
import com.flab.pokerunner.domain.dto.UserSignUpRequestDTO;
import com.flab.pokerunner.domain.dto.UuidRequestDTO;
import com.flab.pokerunner.service.user.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @GetMapping("/checkUserNicknameExist/{user_nickname}")
    public ResponseEntity<Boolean> checkUserNicknameExists(@PathVariable String user_nickname) {
        boolean userNicknameExists = userService.checkUserNicknameExists(user_nickname);
        return ResponseEntity.ok(userNicknameExists);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserSignUpRequestDTO userSignUpRequestDTO) {
        boolean isnNewUser = userService.signup(userSignUpRequestDTO);
        if (isnNewUser) {
            return new ResponseEntity<>("User created successfully.", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("User uuid already exists.", HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/pokemons/{user_uuid}")
    public ResponseEntity<List<UserPokemonDto>> getUserPokemons(@PathVariable String user_uuid) {
        List<UserPokemonDto> userPokemonDtos = userService.getUserPokemons(user_uuid);
        return ResponseEntity.ok(userPokemonDtos);
    }

    @PutMapping("/pokemon/default")
    public ResponseEntity<String> setDefaultPokemon(@RequestBody UserSetDefaultPokemonDto userSetDefaultPokemonDto) {
        userService.setDefaultPokemon(userSetDefaultPokemonDto);
        return ResponseEntity.ok("Pokemon saved as default.");
    }

    @PostMapping("/pokemon")
    public ResponseEntity<String> addUserPokemon(@RequestBody AddPokemonDto addPokemonDto){
        userService.addUserPokemon(addPokemonDto);
        return ResponseEntity.ok("New Pokemon saved.");
    }

    @GetMapping("/runnings/{user_uuid}")
    public ResponseEntity<List<UserRunningInfoDto>> getUserRunnings(@PathVariable String user_uuid){
        List<UserRunningInfoDto> userRunningInfoDtos = userService.getUserRunningInfo(user_uuid);
        return ResponseEntity.ok(userRunningInfoDtos);
    }



}
