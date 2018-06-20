package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.TimeUtil.*;


public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> map = new HashMap<>();

        for (UserMeal meal : mealList) {
            map.merge(meal.getLocalDate(), meal.getCalories(), (a, b) -> a + b);
        }
        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();
        for (UserMeal meal : mealList) {
            if (TimeUtil.isBetween(meal.getLocalTime(), startTime, endTime))
                if (map.get(meal.getLocalDate()) > caloriesPerDay) {
                    userMealWithExceeds.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), true));
                } else
                    userMealWithExceeds.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), false));
        }

        return userMealWithExceeds;
    }

    public static List<UserMealWithExceed> getFilteredWithStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> map = mealList
                .stream()
                .collect(Collectors.toMap(UserMeal::getLocalDate, UserMeal::getCalories, (a, b) -> a + b));

        return mealList
                .stream()
                .filter(t -> isBetween(t.getLocalTime(), startTime, endTime))
                .map(m -> new UserMealWithExceed(m.getDateTime(), m.getDescription(), m.getCalories(), map.get(m.getLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
