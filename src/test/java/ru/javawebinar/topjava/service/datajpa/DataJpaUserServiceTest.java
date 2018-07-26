package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(value = Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getWithMeal() {
        User withMeal=service.getWithMeal(UserTestData.USER_ID);
        log.info(withMeal.toString());
    }
}
