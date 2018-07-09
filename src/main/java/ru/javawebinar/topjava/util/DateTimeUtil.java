package ru.javawebinar.topjava.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable<T>> boolean isBetween(T point, T start, T end) {
        return point.compareTo(start) >= 0 && point.compareTo(end) <= 0;
    }

    public static <T> T check(T value, T def) {
        return value == null ? def : value;

    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }


    public static LocalDate getLocalDate (String str) {
        return str.isEmpty() ? null : LocalDate.parse(str);
    }

    public static LocalTime getLocalTime (String str) {
        return str.isEmpty() ? null : LocalTime.parse(str);
    }

}

