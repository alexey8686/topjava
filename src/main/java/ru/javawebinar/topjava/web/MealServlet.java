package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
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

    private List<MealWithExceed> mealsWithExceeded;
    private InMemoryMealRepo memoryMealRepo;

    @Override
    public void init() throws ServletException {
        memoryMealRepo = new InMemoryMealRepo();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String id = request.getParameter("id");

        switch (action == null ? "all" : action) {
            case "all":
                request.setAttribute("meals", MealsUtil.getFilteredWithExceeded(memoryMealRepo.getAll()
                        , LocalTime.MIN,LocalTime.MAX, 2000));
                forwardTo("/meals.jsp", request, response);
                break;
            case "update":
                request.setAttribute("meal", memoryMealRepo.get(Integer.valueOf(id)));
                forwardTo("/mealsFormEdit.jsp", request, response);
                break;
            case "create":
                request.setAttribute("meal", new Meal());
                forwardTo("/mealsFormEdit.jsp", request, response);
                break;
            case "delete":
                memoryMealRepo.delete(Integer.valueOf(id));
                response.sendRedirect("meals");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("save");
        String id = request.getParameter("id");
        if (action.equals("save")) {
            memoryMealRepo.save(new Meal(id.isEmpty() ? null : Integer.valueOf(id)
                    , LocalDateTime.parse(request.getParameter("dateTime"))
                    , request.getParameter("description")
                    , Integer.valueOf(request.getParameter("calories"))));
        }
        response.sendRedirect("meals");
    }

    public void forwardTo(String jsp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher(jsp).forward(request, response);
    }
}
