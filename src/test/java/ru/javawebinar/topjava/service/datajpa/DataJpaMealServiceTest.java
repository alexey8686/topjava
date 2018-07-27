package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.MEAL1;
import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles(value = Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {

    @Test
    public void getWithUser() {
        Meal meal = service.getWithUser(MealTestData.MEAL1_ID, UserTestData.USER_ID);
        assertMatch(MEAL1, meal);
        UserTestData.assertMatch(UserTestData.USER, meal.getUser());
    }

    @Test
    public void getWithUserNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.getWithUser(MEAL1_ID, ADMIN_ID);
    }


}
