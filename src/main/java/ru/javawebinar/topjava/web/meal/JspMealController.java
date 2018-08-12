package ru.javawebinar.topjava.web.meal;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    @RequestMapping(method = RequestMethod.GET, value = "/delete")
    public String deleteOne(HttpServletRequest request) {
        super.delete(getId(request));
        return "redirect:/meals";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/create")
    public String create(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/update")
    public String update(HttpServletRequest request, Model model) {
        Meal meal = super.get(getId(request));
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public String save(HttpServletRequest request) {
        Meal meal = new Meal(LocalDateTime.parse(request.getParameter("dateTime"))
                , request.getParameter("description")
                , Integer.parseInt(request.getParameter("calories")));

        if (request.getParameter("id").isEmpty()) {
            super.create(meal);
        } else {
            super.update(meal, getId(request));
        }
        return "redirect:/meals";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/filtering")
    public String filter(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", super.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    public Integer getId(HttpServletRequest request) {
        return request.getParameter("id") == null ? null : Integer.parseInt(request.getParameter("id"));
    }

}
