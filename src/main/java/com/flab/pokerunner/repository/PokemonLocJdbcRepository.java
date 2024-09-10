package com.flab.pokerunner.repository;

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


    public List<String> findPokemonLocationsNearby(String lon, String lat, String radius) {
        String sql = """
                SELECT pokemon_name 
                FROM pokemon_location_real_time 
                WHERE ST_Distance_Sphere(coordinates, ST_GeomFromText(CONCAT('POINT(', ?, ' ', ?, ')'))) <= ?
                """;

        return jdbcTemplate.query(sql,
                new Object[]{lon, lat, radius},
                (rs, rowNum) -> rs.getString("pokemon_name")
        );
    }
}
