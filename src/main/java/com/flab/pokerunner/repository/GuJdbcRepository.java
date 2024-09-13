package com.flab.pokerunner.repository;

import com.flab.pokerunner.domain.dto.gu.GuBossDto;
import com.flab.pokerunner.domain.dto.gu.GuBossRankDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class GuJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<GuBossDto> getGuBossList() {
        String sql = """
                WITH RankedRuns AS (SELECT user_id,
                                           gu_address,
                                           SUM(distance_meter)                                                           AS totalDistance,
                                           ROW_NUMBER() OVER (PARTITION BY gu_address ORDER BY SUM(distance_meter) DESC) AS rn
                                    FROM user_running
                                    GROUP BY user_id, gu_address)
                SELECT u.id          AS userId,
                       u.nickname    AS userNickname,
                       rr.gu_address AS runningAddress,
                       rr.totalDistance,
                       p.pokemon_name,
                       p.image_url
                FROM RankedRuns rr
                         JOIN users u ON rr.user_id = u.id
                         JOIN pokemon p ON u.default_pokemon_id = p.id
                WHERE rr.rn = 1
                ORDER BY rr.totalDistance DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> GuBossDto.builder()
                .userId(rs.getInt("userId"))
                .userNickname(rs.getString("userNickname"))
                .runningAddress(rs.getString("runningAddress"))
                .totalDistance(rs.getString("totalDistance"))
                .pokemonName(rs.getString("pokemon_name"))
                .imageUrl(rs.getString("image_url"))
                .build());
    }

    public List<GuBossRankDto> getGuBossByGuLimit3(String guAddress) {
        String sql = """
                WITH ranked_users AS (SELECT u.id                                                                      AS userId,
                                             u.comment                                                                 AS comment,
                                             u.nickname                                                                AS userNickname,
                                             ur.gu_address                                                             AS runningAddress,
                                             SUM(CAST(ur.distance_meter AS DECIMAL))                                   AS totalDistanceMeter,
                                             up.nickname                                                               AS pokemonName,
                                             up.experience                                                             AS exp,
                                             up.level                                                                  AS level,
                                             p.image_url                                                               AS imageUrl,
                                             ROW_NUMBER() OVER (ORDER BY SUM(CAST(ur.distance_meter AS DECIMAL)) DESC) AS ranking
                                      FROM user_running ur
                                               JOIN users u ON u.id = ur.user_id
                                               JOIN user_pokemon up ON u.default_pokemon_id = up.pokemon_id
                                               JOIN pokemon p ON u.default_pokemon_id = p.id
                                      WHERE ur.gu_address = ?
                                      GROUP BY u.id, u.nickname, ur.gu_address, up.nickname, up.experience, up.level, p.image_url)
                SELECT ranking,
                       userId,
                       comment,
                       userNickname,
                       runningAddress,
                       totalDistanceMeter,
                       pokemonName,
                       exp,
                       level,
                       imageUrl
                FROM ranked_users
                ORDER BY ranking
                LIMIT 3
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> GuBossRankDto.builder()
                .ranking(rs.getInt("ranking"))
                .userId(rs.getInt("userId"))
                .comment(rs.getString("comment"))
                .userNickname(rs.getString("userNickname"))
                .runningAddress(rs.getString("runningAddress"))
                .totalDistanceMeter(rs.getString("totalDistanceMeter"))
                .pokemonName(rs.getString("pokemonName"))
                .exp(rs.getInt("exp"))
                .level(rs.getInt("level"))
                .imageUrl(rs.getString("imageUrl"))
                .build(), guAddress);
    }
}
