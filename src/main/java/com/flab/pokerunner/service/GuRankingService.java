package com.flab.pokerunner.service;

import com.flab.pokerunner.repository.running.UserRunningJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuRankingService {
    private final UserRunningJdbcRepository userRunningJdbcRepository;
}
