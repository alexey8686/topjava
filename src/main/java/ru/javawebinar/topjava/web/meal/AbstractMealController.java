package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.getAuthUserId;

public abstract class AbstractMealController {
    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll(int userId, int caloriesPerDay) {
        return MealsUtil.getWithExceeded(service.getAll(userId), caloriesPerDay);
    }

    public Meal create(Meal meal) {
        return service.create(getAuthUserId(), meal);
    }

    public void update(Meal meal) {
        service.update(getAuthUserId(), meal);
    }

    public void delete(int id) {
        service.delete(getAuthUserId(), id);
    }

    public Meal get(int id) {
        return service.get(getAuthUserId(), id);
    }

    public List<MealWithExceed> getFiltered(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        return MealsUtil.getFilteredWithExceeded(service.getFiltered(getAuthUserId(), startDate, endDate),
                authUserCaloriesPerDay(),
                startTime,
                endTime);


    }
}


