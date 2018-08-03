package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends MealRestController {


    public JspMealController(MealService service) {
        super(service);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView meals(ModelAndView modelAndView) {
        modelAndView.addObject("meals", super.getAll());
        modelAndView.setViewName("meals");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete/{id}")
    public ModelAndView deleteMeal(@PathVariable("id") int id) {
        super.delete(id);
        return new ModelAndView("redirect:/meals");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/create")
    public ModelAndView createMeal() {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        return new ModelAndView("/mealForm", "meal", meal);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/update")
    public ModelAndView updateMeal(HttpServletRequest request) {
        Meal meal = super.get(Integer.valueOf(request.getParameter("id")));
        return new ModelAndView("/mealForm", "meal", meal);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public ModelAndView saveMeal(HttpServletRequest request) {
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime"))
                , request.getParameter("description")
                , Integer.parseInt(request.getParameter("calories")));

        if (request.getParameter("id").isEmpty()) {
            super.create(meal);
        } else {
            super.update(meal, Integer.parseInt(request.getParameter("id")));
        }
        return new ModelAndView("redirect:/meals");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/filtering")
    public ModelAndView filterMeal(HttpServletRequest request) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        return new ModelAndView("meals"
                , "meals", super.getBetween(startDate, startTime, endDate, endTime));
    }

}
