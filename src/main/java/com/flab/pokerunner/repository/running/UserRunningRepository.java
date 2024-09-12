package com.flab.pokerunner.repository.running;

import com.flab.pokerunner.domain.entity.UserRunningJpo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRunningRepository extends JpaRepository<UserRunningJpo, Integer> {
    UserRunningJpo findByUserId(int userId);

    List<UserRunningJpo> findAllByUserId(int userId);
}
