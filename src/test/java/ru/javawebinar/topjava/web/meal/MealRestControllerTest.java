package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;

public class MealRestControllerTest extends AbstractControllerTest {

    @Test
    void getAllTest() throws Exception {
        mockMvc.perform(get("/rest/meals"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MealsUtil.getWithExceeded(MEALS, USER.getCaloriesPerDay())));

    }

    @Test
    void getTest() throws Exception {
        mockMvc.perform(get("/rest/meals/100002"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(contentJson(MEAL1));
    }

    @Test
    void deleteTest() throws Exception {

    }

    @Test
    void updateTest() throws Exception {
    }

    @Test
    void createsTest() throws Exception {
    }

    @Test
    void getBetweenTest() throws Exception {
    }
}
