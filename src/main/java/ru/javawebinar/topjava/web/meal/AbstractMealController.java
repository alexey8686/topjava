package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

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

}
