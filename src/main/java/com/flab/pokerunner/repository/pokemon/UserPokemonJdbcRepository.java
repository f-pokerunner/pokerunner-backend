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
                SELECT u.id                AS userId,
                       up.experience       AS exp,
                       up.evolution_status AS evolutionStatus,
                       up.level            AS level,
                       p.pokemon_name      AS pokemonName,
                       p.image_url         AS imageUrl
                FROM users u
                         JOIN user_pokemon up ON up.user_id = u.id AND u.default_pokemon_id = up.pokemon_id
                         JOIN pokemon p ON up.pokemon_id = p.id
                WHERE u.id = ?
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new UserPokemonDao(
                rs.getInt("userId"),
                rs.getInt("exp"),
                rs.getInt("evolutionStatus"),
                rs.getInt("level"),
                rs.getString("pokemonName"),
                rs.getString("imageUrl")
        ), userId);
    }
}
