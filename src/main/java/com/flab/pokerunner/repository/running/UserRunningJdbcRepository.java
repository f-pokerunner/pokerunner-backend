package com.flab.pokerunner.repository.running;

import com.flab.pokerunner.domain.dto.UserRunningSummaryDto;
import com.flab.pokerunner.domain.dto.UserRunningTimeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRunningJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<UserRunningSummaryDto> findUserRunningSummary() {
        String sql = """
                WITH RankedRuns AS (SELECT user_id,
                                           gu_address,
                                           SUM(distance_meter)                                                           AS totalDistance,
                                           ROW_NUMBER() OVER (PARTITION BY gu_address ORDER BY SUM(distance_meter) DESC) AS rn
                                    FROM user_running
                                    GROUP BY user_id, gu_address)
                SELECT user_id    AS userId,
                       gu_address AS guAddress,
                       totalDistance
                FROM RankedRuns
                WHERE rn = 1
                ORDER BY totalDistance DESC;                
                """;
        return jdbcTemplate.query(sql, userRunningSummaryDtoRowMapper());
    }

    public List<UserRunningTimeDto> findUserRunningTimeList() {
        String sql = """
                SELECT user_id,
                       MAX(start_time) AS earliestStartTime
                FROM user_running
                WHERE distance_meter > 10
                GROUP BY user_id
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> new UserRunningTimeDto(
                rs.getInt("user_id"),
                rs.getTimestamp("earliestStartTime").toLocalDateTime()
        ));
    }

    private RowMapper<UserRunningSummaryDto> userRunningSummaryDtoRowMapper() {
        return (rs, rowNum) -> new UserRunningSummaryDto(
                rs.getInt("userId"),
                rs.getString("guAddress"),
                rs.getString("totalDistance")
        );
    }


}
