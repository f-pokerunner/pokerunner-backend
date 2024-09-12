package com.flab.pokerunner.service.cron;

import com.flab.pokerunner.domain.dto.running.UserRunningTimeDto;
import com.flab.pokerunner.domain.entity.UserPokemonJpo;
import com.flab.pokerunner.repository.pokemon.UserPokemonRepository;
import com.flab.pokerunner.repository.running.UserRunningJdbcRepository;
import com.flab.pokerunner.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PokemonHealthUpdateCron {

    private final UserRunningJdbcRepository userRunningJdbcRepository;
    private final UserPokemonRepository userPokemonRepository;

    @Scheduled(cron = "0 30 0 * * ?")
    @Transactional
    public void updatePokemonHealth() {
        List<UserRunningTimeDto> userRunningTimeList = userRunningJdbcRepository.findUserRunningTimeList();
        userRunningTimeList.forEach(userRunningTime -> {
            boolean isOverThreeDays = DateUtil.isMoreThanThreeDays(userRunningTime.earliestStartTime);
            if (isOverThreeDays) {
                UserPokemonJpo foundPokemon = userPokemonRepository.findByUserIdAndPokemonId(userRunningTime.getUserId(), userRunningTime.getDefaultPokemonId());
                foundPokemon.subtractHealth(50);
            }
        });
    }
}
