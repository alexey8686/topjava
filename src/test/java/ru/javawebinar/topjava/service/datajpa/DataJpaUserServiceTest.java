package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.UserTestData.UNKNOWN_USR;

@ActiveProfiles(value = Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getWithMeal() {
        User user = service.getWithMeal(UserTestData.USER_ID);
        UserTestData.assertMatch(UserTestData.USER, user);
        MealTestData.assertMatch(MealTestData.MEALS, user.getMeal());
    }

    @Test
    public void getWithMealNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.getWithMeal(UNKNOWN_USR);
    }
}
