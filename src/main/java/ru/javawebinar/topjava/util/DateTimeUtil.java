package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static boolean isBetweenDate(LocalDate ld, String startDate, String endDate) {
        if (startDate.equals(""))
            startDate = LocalDate.of(1900, 01, 01).toString();

        if (endDate.equals(""))
            endDate = LocalDate.of(2100, 12, 31).toString();

        return ld.compareTo(LocalDate.parse(startDate)) >= 0 && ld.compareTo(LocalDate.parse(endDate)) <= 0;
    }

    public static boolean isBetweenTime(LocalTime lt, String startTime, String endTime) {
        if (startTime.equals(""))
            startTime = LocalTime.MIN.toString();

        if (endTime.equals(""))
            endTime = LocalTime.MAX.toString();

        return lt.compareTo(LocalTime.parse(startTime)) >= 0 && lt.compareTo(LocalTime.parse(endTime)) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

    public static LocalDate getDate(String date) {
        if (!date.isEmpty())
            return LocalDate.parse(date);
        else
            return null;

    }

    public static LocalTime getTime(String time) {
        if (!time.isEmpty())
            return LocalTime.parse(time);
        else
            return null;
    }
}

