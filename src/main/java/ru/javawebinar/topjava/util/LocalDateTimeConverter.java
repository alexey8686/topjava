package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


class CustomConverter   {

    public class LocalTimeConverter implements Converter<String, LocalTime>{

        @Override
        public LocalTime convert(String source) {
            if (source == null || source.isEmpty()) {
                return null;
            }
            return LocalDateTime.parse(source,DateTimeUtil.DATE_TIME_FORMATTER).toLocalTime();
        }
    }
    public class LocalDateConverter implements Converter<String, LocalDate>{

        @Override
        public LocalDate convert(String source) {
            if (source == null || source.isEmpty()) {
                return null;
            }
            return LocalDateTime.parse(source,DateTimeUtil.DATE_TIME_FORMATTER).toLocalDate();
        }
    }
}
