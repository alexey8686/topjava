package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


import static ru.javawebinar.topjava.util.DateTimeUtil.check;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.getAuthUserId;

public abstract class AbstractMealController {
    @Autowired
    private MealService service;

    private static final Logger log = LoggerFactory.getLogger(AbstractMealController.class);

    public List<MealWithExceed> getAll() {
        log.info("getAll for user {}", getAuthUserId());
        return MealsUtil.getWithExceeded(service.getAll(getAuthUserId()), authUserCaloriesPerDay());
    }

    public Meal create(Meal meal) {
        log.info("create {} for user {}", meal, getAuthUserId());
        return service.create(getAuthUserId(), meal);
    }

    public void update(Meal meal) {
        log.info("update {} for user {}", meal, getAuthUserId());
        service.update(getAuthUserId(), meal);
    }

    public void delete(int id) {
        log.info("delete meal {} for user {}", id, getAuthUserId());
        service.delete(getAuthUserId(), id);
    }

    public Meal get(int id) {
        log.info("get meal {} for user {}", id, getAuthUserId());
        return service.get(getAuthUserId(), id);
    }

    public List<MealWithExceed> getFiltered(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, getAuthUserId());
        List<Meal> mealsDateFiltered = service.getFiltered(getAuthUserId(),
                check(startDate, LocalDate.of(1900, 1, 1)),
                check(endDate, LocalDate.of(3000, 12, 31)));

        return MealsUtil.getFilteredWithExceeded(mealsDateFiltered,
                authUserCaloriesPerDay(),
                startTime,
                endTime);


    }
}


