package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    public MealServiceTest() {
    }



    @Test
    public void get() {
        Meal meal = mealService.get(100002, USER_ID);
        assertMatch(meal, MEAL1);
    }
    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        mealService.get(1,USER_ID);
    }

    @Test
    public void delete() {
        mealService.delete(MEAL6.getId(),USER_ID);
        List<Meal> meals = mealService.getAll(USER_ID);
        assertMatch(meals,MEAL5,MEAL4,MEAL3,MEAL2,MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        mealService.delete(1,USER_ID);
    }



    @Test
    public void getBetweenDateTimes() {
    List<Meal> meals = mealService.getBetweenDateTimes(LocalDateTime.of(2018, Month.JULY, 10, 10, 0),
            LocalDateTime.of(2018, Month.JULY, 10, 20, 0),USER_ID);
    assertMatch(meals,MEAL3,MEAL2,MEAL1);
    }

    @Test
    public void getAll() {
        List<Meal> meals = mealService.getAll(USER_ID);
        meals.forEach(System.out::println);
        assertMatch(meals,MEALS);
    }

    @Test
    public void update() {
        Meal updated  = new Meal(MEAL1);
        updated.setCalories(490);
        mealService.update(updated,USER_ID);
        assertMatch(mealService.get(updated.getId(),USER_ID),updated);
    }

    @Test
    public void create() {
        mealService.create(newMEAL,USER_ID);
        assertMatch(mealService.getAll(USER_ID),newMEAL,MEAL6,MEAL5,MEAL4,MEAL3,MEAL2,MEAL1);
    }
}