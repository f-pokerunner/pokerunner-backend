package com.flab.pokerunner.repository.user;

import com.flab.pokerunner.domain.entity.UserJpo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserJpo, Integer> {
    boolean existsByUuid(String uuid);

    boolean existsByNickname(String nickname);

    UserJpo findByUuid(String uuid);
}
