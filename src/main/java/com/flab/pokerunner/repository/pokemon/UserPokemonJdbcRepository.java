package com.flab.pokerunner.repository.pokemon;

import com.flab.pokerunner.domain.dto.pokemon.UserPokemonDao;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserPokemonJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserPokemonDao findUserPokemonByUserId(int userId) {
        String sql = """
                SELECT u.id AS userId,
                       p.experience AS exp,
                       p.evolution_status AS evolutionStatus
                FROM users u
                         JOIN user_pokemon p ON u.default_pokemon_id = p.pokemon_id
                WHERE u.id = ?
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new UserPokemonDao(
                rs.getInt("userId"),
                rs.getInt("exp"),
                rs.getInt("evolutionStatus")
        ), userId);
    }
}
