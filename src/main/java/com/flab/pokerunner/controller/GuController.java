package com.flab.pokerunner.controller;

import com.flab.pokerunner.domain.entity.SeoulGuJpo;
import com.flab.pokerunner.repository.SeoulGuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/gu")
@RequiredArgsConstructor
@RestController
public class GuController {

    private final SeoulGuRepository seoulGuRepository;

    @GetMapping
    public ResponseEntity<List<String>> guList() {
        List<SeoulGuJpo> seoulGuJpoList = seoulGuRepository.findAll();

        List<String> guNames = seoulGuJpoList.stream()
                .map(SeoulGuJpo::getGuName)
                .toList();

        return ResponseEntity.ok(guNames);
    }
}
