package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {


    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.stream().map(meal -> {
            if (meal.isNew()) {
                meal.setId(counter.incrementAndGet());
                repository.put(meal.getId(), meal);
            }
            return meal;
        }).collect(Collectors.toList());
    }

    @Override
    public Meal save(int userId, Meal meal) {
        if (meal.isNew() && meal.getUserId() == userId) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        if (repository.get(meal.getId()).getUserId() == userId)
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        else
            return null;
    }

    @Override
    public boolean delete(int userId, int id) {
        if (repository.get(id).getUserId() == userId)
            return repository.remove(id) != null;
        return false;
    }

    @Override
    public Meal get(int userId, int id) {
        if (repository.containsKey(id) && repository.get(id).getUserId() == userId)
            return repository.get(id);
        else
            return null;

    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAllCommon(userId)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed()).
                        collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate) {

        return getAllCommon(userId)
                .filter(d -> DateTimeUtil.isBetweenDate(d.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDateTime))
                .collect(Collectors.toList());
    }

    public Stream<Meal> getAllCommon(int userId){
        return repository.values().stream().filter(m -> m.getUserId() == userId);
    }


}

