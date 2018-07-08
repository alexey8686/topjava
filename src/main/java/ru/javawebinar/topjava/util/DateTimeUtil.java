package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenDate(LocalDate ld, LocalDate startDate, LocalDate endDate) {
        if (startDate == null)
            startDate = LocalDate.of(1900, 0, 0);

        if (endDate == null)
            endDate = LocalDate.of(2100, 12, 31);

        return ld.compareTo(startDate) >= 0 && ld.compareTo(endDate) <= 0;
    }

    public static boolean isBetweenTime(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        if (startTime == null)
            startTime = LocalTime.MIN;

        if (endTime == null)
            endTime = LocalTime.MAX;

        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }


    public static LocalDate checkLocalDate(String str) {
        return str.isEmpty() ? null : LocalDate.parse(str);
    }

    public static LocalTime checkLocalTime(String str) {
        return str.isEmpty() ? null : LocalTime.parse(str);
    }
}

