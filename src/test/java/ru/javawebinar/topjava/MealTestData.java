package ru.javawebinar.topjava;


import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static AtomicInteger integer = new AtomicInteger(100001);


    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(integer.incrementAndGet(),LocalDateTime.of(2018, Month.JULY, 10, 10, 0), "Завтрак", 500),
            new Meal(integer.incrementAndGet(),LocalDateTime.of(2018, Month.JULY, 10, 13, 0), "Обед", 1000),
            new Meal(integer.incrementAndGet(),LocalDateTime.of(2018, Month.JULY, 10, 20, 0), "Ужин", 500),
            new Meal(integer.incrementAndGet(),LocalDateTime.of(2018, Month.JULY, 11, 10, 0), "Завтрак", 1000),
            new Meal(integer.incrementAndGet(),LocalDateTime.of(2018, Month.JULY, 11, 13, 0), "Обед", 500),
            new Meal(integer.incrementAndGet(),LocalDateTime.of(2018, Month.JULY, 11, 20, 0), "Ужин", 510),
            new Meal(integer.incrementAndGet(),LocalDateTime.of(2018, Month.JULY, 11, 10, 0), "Завтрак", 1000),
            new Meal(integer.incrementAndGet(),LocalDateTime.of(2018, Month.JULY, 11, 13, 0), "Обед", 500),
            new Meal(integer.incrementAndGet(),LocalDateTime.of(2018, Month.JULY, 11, 20, 0), "Ужин", 510)
    );


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}
