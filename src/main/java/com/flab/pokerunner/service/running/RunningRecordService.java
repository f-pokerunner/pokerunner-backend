package com.flab.pokerunner.service.running;

import com.flab.pokerunner.domain.entity.UserRunningJpo;
import com.flab.pokerunner.repository.running.UserRunningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RunningRecordService {
    private final UserRunningRepository userRunningRepository;

    public void save(UserRunningJpo userRunningJpo) {
        int userId = userRunningJpo.userId;
        UserRunningJpo foundUserRunningJpo = userRunningRepository.findByUserId(userId);

        if (foundUserRunningJpo != null) {
            foundUserRunningJpo.distanceMeter = userRunningJpo.distanceMeter;
            foundUserRunningJpo.endTime = userRunningJpo.endTime;
            foundUserRunningJpo.pace = userRunningJpo.pace;
            userRunningRepository.save(foundUserRunningJpo);
        } else {
            userRunningRepository.save(userRunningJpo);
        }
    }
}
