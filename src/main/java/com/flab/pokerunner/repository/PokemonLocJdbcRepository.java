package com.flab.pokerunner.repository;

import com.flab.pokerunner.domain.dto.PokemonLocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PokemonLocJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public void insertPokemonLocation(String lat, String lon, String pokemon) {
        String sql = "INSERT INTO pokemon_location_real_time (pokemon_name, coordinates) VALUES (?, ST_PointFromText(CONCAT('POINT(', ?, ' ', ?, ')')))";
        jdbcTemplate.update(sql, pokemon, lon, lat);
    }


    public List<PokemonLocationDto> findPokemonLocationsNearby(String lon, String lat, String radius) {
        String sql = """
                SELECT RT.id              AS id,
                       P.pokemon_name     AS pokemonName,
                       P.id               AS id,
                       P.evolution_status AS evolutionStatus,
                       P.image_url        AS imageUrl
                FROM pokemon_location_real_time RT
                         JOIN pokemon P ON RT.pokemon_name = P.pokemon_name
                WHERE ST_Distance_Sphere(coordinates, ST_GeomFromText(CONCAT('POINT(', ?, ' ', ?, ')'))) <= ?
                  AND owner_id IS NULL;
                """;

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new PokemonLocationDto(
                        rs.getInt("id"),
                        rs.getString("pokemonName"),
                        rs.getInt("id"),
                        rs.getString("evolutionStatus"),
                        rs.getString("imageUrl")
                ), lon, lat, radius
        );
    }

    public void updateOwnerId(int locationId, int ownerId) {
        String sql = "UPDATE pokemon_location_real_time SET owner_id = ? WHERE id = ?";
        jdbcTemplate.update(sql, ownerId, locationId);
    }
}
