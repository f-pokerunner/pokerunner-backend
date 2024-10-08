package com.flab.pokerunner.controller;

import com.flab.pokerunner.domain.dto.*;
import com.flab.pokerunner.domain.dto.running.UserRunningInfoDto;
import com.flab.pokerunner.service.user.UserCommentService;
import com.flab.pokerunner.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
    private final UserCommentService userCommentService;

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
    public ResponseEntity<Integer> signup(@RequestBody UserSignUpRequestDTO userSignUpRequestDTO) {
        int userId = userService.signup(userSignUpRequestDTO);
        return ResponseEntity.ok(userId);
    }

    @GetMapping("/pokemons/{user_uuid}")
    public ResponseEntity<List<UserPokemonDto>> getUserPokemons(@PathVariable String user_uuid) {
        List<UserPokemonDto> userPokemonDtos = userService.getUserPokemons(user_uuid);
        return ResponseEntity.ok(userPokemonDtos);
    }

    @PutMapping("/pokemon/default")
    public ResponseEntity<String> setDefaultPokemon(@RequestBody UserSetDefaultPokemonDto userSetDefaultPokemonDto) {
        boolean success = userService.setDefaultPokemon(userSetDefaultPokemonDto);
        if (success) {
            return ResponseEntity.ok("Pokémon saved as default.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requested Pokémon not found.");
        }
    }

    @PostMapping("/pokemon")
    public ResponseEntity<String> addUserPokemon(@RequestBody AddPokemonDto addPokemonDto) {
        userService.addUserPokemon(addPokemonDto);
        return ResponseEntity.ok("New Pokemon saved.");
    }

    @GetMapping("/runnings/{user_uuid}")
    public ResponseEntity<List<UserRunningInfoDto>> getUserRunnings(@PathVariable String user_uuid) {
        List<UserRunningInfoDto> userRunningInfoDtos = userService.getUserRunningInfo(user_uuid);
        return ResponseEntity.ok(userRunningInfoDtos);
    }

    @GetMapping("/comments")
    public ResponseEntity<UserCommentDto> getUserComment(@RequestParam int userId) {
        UserCommentDto userCommentInfo = userCommentService.getUserCommentInfo(userId);
        return ResponseEntity.ok(userCommentInfo);
    }

    @PostMapping("/comments")
    public ResponseEntity<UserCommentDto> addUserComment(@RequestBody UserCommentDto userCommentDto) {
        UserCommentDto responseDto = userCommentService.addUserComment(userCommentDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/home/{user_uuid}")
    public ResponseEntity<UserHomeDto> getHomeInfo(@PathVariable String user_uuid) {
        UserHomeDto userHomeDto = userService.getUserHomeInfo(user_uuid);
        return ResponseEntity.ok(userHomeDto);
    }
}
