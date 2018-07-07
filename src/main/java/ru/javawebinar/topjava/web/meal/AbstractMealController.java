package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.getAuthUserId;

public abstract class AbstractMealController {
    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll(int userId, int caloriesPerDay) {
        return MealsUtil.getWithExceeded(service.getAll(userId), caloriesPerDay);
    }

    public Meal create(int userId, Meal meal) {
        return service.create(userId, meal);
    }

    public void update(int userId, Meal meal) {
        service.update(userId, meal);
    }

    public void delete(int userId, int id) {
        service.delete(userId, id);
    }

    public Meal get(int userId, int id) {
        return service.get(userId, id);
    }

    public List<MealWithExceed> getFiltered(String startDate, String startTime, String endDate, String endTime) {
        return MealsUtil.getWithExceeded(service.getFiltered(getAuthUserId(), startDate, endDate), authUserCaloriesPerDay()).
                stream().
                filter(f -> DateTimeUtil.isBetweenTime(f.getDateTime().toLocalTime(), startTime, endTime)).
                collect(Collectors.toList());


    }
}


