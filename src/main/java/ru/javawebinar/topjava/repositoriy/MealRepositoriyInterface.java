package ru.javawebinar.topjava.repositoriy;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepositoriyInterface {

    List<Meal> getAll();

    void delete(int id);

    Meal get(Integer id);

    Meal save(Meal meal);
}
