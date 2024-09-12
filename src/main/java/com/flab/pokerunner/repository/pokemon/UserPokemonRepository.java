package com.flab.pokerunner.repository.pokemon;

import com.flab.pokerunner.domain.entity.UserPokemonJpo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPokemonRepository extends JpaRepository<UserPokemonJpo, Integer> {
    UserPokemonJpo findByUserId(int userId);

    List<UserPokemonJpo> findAllByUserUuid(String userUuid);
}
