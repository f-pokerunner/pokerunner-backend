package com.flab.pokerunner.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class DateUtilTest {
    @Test
    void isMoreThanThreeDaysTest() {
        LocalDateTime oldDate = LocalDateTime.of(2023, 3, 1, 0, 0, 0);
        boolean moreThanThreeDays = DateUtil.isMoreThanThreeDays(oldDate);
        Assertions.assertThat(moreThanThreeDays).isTrue();
    }
}