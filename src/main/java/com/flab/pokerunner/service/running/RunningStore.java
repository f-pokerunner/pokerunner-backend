package com.flab.pokerunner.service.running;

import com.flab.pokerunner.domain.entity.UserRunningJpo;
import com.flab.pokerunner.repository.running.UserRunningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RunningStore {
    private final UserRunningRepository userRunningRepository;

    public int save(UserRunningJpo userRunningJpo) {
        return userRunningRepository.save(userRunningJpo).getId();
    }

    public UserRunningJpo findById(int id) {
        return userRunningRepository.findById(id).orElse(null);
    }
}
