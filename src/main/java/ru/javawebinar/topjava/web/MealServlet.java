package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repositoriy.InMemoryMealRepo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class MealServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);
    private InMemoryMealRepo memoryMealRepo;

    @Override
    public void init() throws ServletException {
        memoryMealRepo = new InMemoryMealRepo();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "all":
                request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(memoryMealRepo.getAll(),
                        LocalTime.MIN, LocalTime.MAX, 2000));
                LOG.info("give all meal");
                forwardTo("/meals.jsp", request, response);
                break;
            case "update":
                request.setAttribute("meal", memoryMealRepo.get(Integer.valueOf(request.getParameter("id"))));
                LOG.info("update meal");
                forwardTo("/mealsFormEdit.jsp", request, response);
                break;
            case "create":
                request.setAttribute("meal", new Meal());
                LOG.info("create meal");
                forwardTo("/mealsFormEdit.jsp", request, response);
                break;
            case "delete":
                memoryMealRepo.delete(Integer.valueOf(request.getParameter("id")));
                LOG.info("delete meal");
                response.sendRedirect("meals");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");
        memoryMealRepo.save(new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories"))));
        LOG.info("save");

        response.sendRedirect("meals");
    }

    private void forwardTo(String jsp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(jsp).forward(request, response);
    }
}
