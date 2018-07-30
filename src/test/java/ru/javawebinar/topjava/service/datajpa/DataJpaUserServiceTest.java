package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(value = Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Autowired
    private MealService mealService;
    @Test
    public void getWithMeal() {
        User user = service.getWithMeal(USER_ID);
        assertMatch(USER, user);
        assertMatch(MEALS, user.getMeal());
    }

    @Test
    public void getWithMealNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        mealService.delete(ADMIN_MEAL1.getId(), ADMIN_ID);
        mealService.delete(ADMIN_MEAL2.getId(),ADMIN_ID);
        service.getWithMeal(ADMIN_ID);
    }


}
