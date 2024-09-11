package com.flab.pokerunner.service.running;

import com.flab.pokerunner.domain.entity.UserRunningJpo;
import com.flab.pokerunner.repository.running.UserRunningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RunningStore {
    private final UserRunningRepository userRunningRepository;

    @Transactional
    public int save(UserRunningJpo userRunningJpo) {
        return userRunningRepository.save(userRunningJpo).getId();
    }

    @Transactional(readOnly = true)
    public UserRunningJpo findById(int id) {
        return userRunningRepository.findById(id).orElse(null);
    }
}
