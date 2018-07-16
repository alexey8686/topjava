package ru.javawebinar.topjava;


import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final int ID_ONE = 100002;
    public static final int ID_TWO = 100010;


    public static final Meal MEAL1 = new Meal(100002, LocalDateTime.of(2018, Month.JULY, 10, 10, 0), "завтрак юзера", 500);
    public static final Meal MEAL2 = new Meal(100003, LocalDateTime.of(2018, Month.JULY, 10, 13, 0), "обед юзера", 500);
    public static final Meal MEAL3 = new Meal(100004, LocalDateTime.of(2018, Month.JULY, 10, 18, 0), "ужин юзера", 1000);
    public static final Meal MEAL4 = new Meal(100005, LocalDateTime.of(2018, Month.JULY, 11, 10, 0), "завтрак юзера", 500);
    public static final Meal MEAL5 = new Meal(100006, LocalDateTime.of(2018, Month.JULY, 11, 13, 0), "обед юзера", 600);
    public static final Meal MEAL6 = new Meal(100007, LocalDateTime.of(2018, Month.JULY, 11, 18, 0), "ужин юзера", 1000);
    public static final Meal ADMIN_MEAL1 = new Meal(100008, LocalDateTime.of(2018, Month.JULY, 11, 10, 0), "завтрак админа", 500);
    public static final Meal ADMIN_MEAL2 = new Meal(100009, LocalDateTime.of(2018, Month.JULY, 11, 13, 0), "обед админа", 600);
    public static final Meal ADMIN_MEAL3 = new Meal(100010, LocalDateTime.of(2018, Month.JULY, 11, 18, 0), "ужин админа", 1000);


    public static final List<Meal> MEALS = Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);


    public static final Meal newMeal = new Meal(LocalDateTime.of(2018, Month.JULY, 12, 20, 0), "Ужин", 510);


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingOnlyGivenFields(expected, "id", "dateTime", "description", "calories");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
