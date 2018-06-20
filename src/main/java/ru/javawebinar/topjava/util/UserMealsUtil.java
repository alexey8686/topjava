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
        Map<LocalDate, Integer> localDateIntegerHashMap = new HashMap<>();

        mealList.forEach(value -> localDateIntegerHashMap.merge(value.getLocalDate(), value.getCalories(), Integer::sum));

        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();

        mealList.forEach(userMeal -> {
            if (TimeUtil.isBetween(userMeal.getLocalTime(), startTime, endTime))
                userMealWithExceeds.add(new UserMealWithExceed(userMeal.getDateTime()
                        , userMeal.getDescription()
                        , userMeal.getCalories()
                        , localDateIntegerHashMap.get(userMeal.getLocalDate()) > caloriesPerDay));
        });

        return userMealWithExceeds;
    }

    public static List<UserMealWithExceed> getFilteredWithStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> localDateIntegerMap = mealList
                .stream()
                .collect(Collectors.toMap(UserMeal::getLocalDate, UserMeal::getCalories, Integer::sum));

        return mealList
                .stream()
                .filter(userMeal -> isBetween(userMeal.getLocalTime(), startTime, endTime))
                .map(userMeal1 -> new UserMealWithExceed(userMeal1.getDateTime(), userMeal1.getDescription(), userMeal1.getCalories(), localDateIntegerMap.get(userMeal1.getLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
