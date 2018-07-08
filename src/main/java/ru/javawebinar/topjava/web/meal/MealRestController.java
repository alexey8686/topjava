package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.util.List;


@Controller
public class MealRestController extends AbstractMealController {

 /*   @Override
    public List<MealWithExceed> getAll(int userId, int caloriesPerDay) {
        return super.getAll(userId, caloriesPerDay);
    }

    @Override
    public Meal create(int userId, Meal meal) {
        return super.create(userId, meal);
    }

    @Override
    public void update(int userId, Meal meal) {
        super.update(userId, meal);
    }

    @Override
    public void delete(int userId, int id) {
        super.delete(userId, id);
    }

    @Override
    public Meal get(int userId, int id) {
        return super.get(userId, id);
    }

    @Override
    public List<MealWithExceed> getFiltered(String startDate, String startTime, String endDate, String endTime) {
        return super.getFiltered(startDate, startTime, endDate, endTime);
    }*/
}