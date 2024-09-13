package com.flab.pokerunner.repository.running;

import com.flab.pokerunner.domain.entity.UserRunningJpo;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRunningRepository extends JpaRepository<UserRunningJpo, Integer> {
    UserRunningJpo findByUserId(int userId);

    List<UserRunningJpo> findAllByUserId(int userId);

    @Query("SELECT MAX(r.endTime) FROM UserRunningJpo r WHERE r.userId = :userId")
    LocalDateTime findLatestEndTimeByUserId(@Param("userId") int userId);
}
