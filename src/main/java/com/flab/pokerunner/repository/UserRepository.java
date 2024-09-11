package com.flab.pokerunner.repository;

import com.flab.pokerunner.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUuid(String uuid);

    User findByUuid(String uuid);

    User findById(int id);
}
