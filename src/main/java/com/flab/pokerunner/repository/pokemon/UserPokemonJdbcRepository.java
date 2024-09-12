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
                       up.evolution_status AS es,
                       p.pokemon_name      AS pokemonName,
                       p.image_url         AS imageUrl
                FROM users u
                         JOIN user_pokemon up ON u.default_pokemon_id = up.pokemon_id
                         JOIN pokemon p ON p.id = up.pokemon_id AND p.evolution_status = up.evolution_status
                WHERE u.id = ?
                """;

        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> new UserPokemonDao(
                rs.getInt("userId"),
                rs.getInt("exp"),
                rs.getInt("evolutionStatus"),
                rs.getString("pokemonName"),
                rs.getString("imageUrl")
        ), userId);
    }
}
