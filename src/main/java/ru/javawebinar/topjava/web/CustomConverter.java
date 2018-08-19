package ru.javawebinar.topjava.web;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalTime;

import static ru.javawebinar.topjava.util.DateTimeUtil.*;


public class CustomConverter {

    public static class LocalTimeConverter implements Converter<String, LocalTime> {

        @Override
        public LocalTime convert(String source) {
            return parseLocalTime(source);
        }
    }

    public static class LocalDateConverter implements Converter<String, LocalDate> {

        @Override
        public LocalDate convert(String source) {
            return parseLocalDate(source);
        }
    }
}
