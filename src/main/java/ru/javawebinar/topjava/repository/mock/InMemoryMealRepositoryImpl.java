package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        if (meal.getUserId() == userId)
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        else
            throw new NotFoundException("no item");
    }

    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        if (repository.get(id).getUserId() == userId) {
            repository.remove(id);
            return true;
        } else
            return false;
    }

    @Override
    public Meal get(int userId, int id) {
        if (repository.get(id).getUserId() == userId)
            return repository.get(id);
        else
            return null;

    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.values().stream().
                filter(m -> m.getUserId() == userId).
                sorted(Comparator.comparing(Meal::getDateTime).reversed()).
                collect(Collectors.toList());
    }

    @Override
    public Collection<Meal> getFiltered(int userId, String startDate, String endDate) {
        return repository.values().stream()
                .filter(u -> u.getUserId() == userId).filter(d -> DateTimeUtil.isBetweenDate(d.getDate(), startDate, endDate))
                .sorted((a, b) -> b.getDateTime().compareTo(a.getDateTime()))
                .collect(Collectors.toList());
    }
}

