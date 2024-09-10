package com.flab.pokerunner.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtil {
    public static boolean isMoreThanThreeDays(LocalDateTime inputDate) {
        LocalDateTime currentDate = LocalDateTime.now();
        return ChronoUnit.DAYS.between(inputDate, currentDate) > 3;
    }
}
