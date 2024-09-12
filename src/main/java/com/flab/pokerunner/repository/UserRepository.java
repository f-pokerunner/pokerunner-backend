package com.flab.pokerunner.repository;

import com.flab.pokerunner.domain.entity.UserJpo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserJpo, Long> {
    boolean existsByUuid(String uuid);

    UserJpo findByUuid(String uuid);

    UserJpo findById(int id);

    boolean existsByNickname(String nickname);
}
