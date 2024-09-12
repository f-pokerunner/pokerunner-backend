package com.flab.pokerunner.controller;

import com.flab.pokerunner.domain.dto.gu.GuBossDto;
import com.flab.pokerunner.domain.dto.gu.GuBossRankDto;
import com.flab.pokerunner.domain.dto.gu.GuRequestDto;
import com.flab.pokerunner.domain.entity.SeoulGuJpo;
import com.flab.pokerunner.repository.GuJdbcRepository;
import com.flab.pokerunner.repository.SeoulGuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/gu")
@RequiredArgsConstructor
@RestController
public class GuController {

    private final SeoulGuRepository seoulGuRepository;
    private final GuJdbcRepository guJdbcRepository;

    @GetMapping
    public ResponseEntity<List<String>> guList() {
        List<SeoulGuJpo> seoulGuJpoList = seoulGuRepository.findAll();

        List<String> guNames = seoulGuJpoList.stream()
                .map(SeoulGuJpo::getGuName)
                .toList();

        return ResponseEntity.ok(guNames);
    }

    @GetMapping("/boss-list")
    public ResponseEntity<List<GuBossDto>> guBossList() {
        List<GuBossDto> guBossList = guJdbcRepository.getGuBossList();
        return ResponseEntity.ok(guBossList);
    }

    @PostMapping("/boss")
    public ResponseEntity<List<GuBossRankDto>> guBossTopThree(@RequestBody GuRequestDto guRequestDto) {
        log.info("dto:{}", guRequestDto.guAddress);
        List<GuBossRankDto> guBossByGuLimit3 = guJdbcRepository.getGuBossByGuLimit3(guRequestDto.guAddress);
        return ResponseEntity.ok(guBossByGuLimit3);
    }
}
