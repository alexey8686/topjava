package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {

    private Map<Integer,Map<Integer,Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
       MealsUtil.MEALS.forEach(meal -> save(1,meal));

       save(2,new Meal(LocalDateTime.of(2018,7,8,12,23,00),"breakfast",100));
       save(2,new Meal(LocalDateTime.of(2018,7,8,14,23,00),"diner",1000));

    }

    @Override
    public Meal save(int userId, Meal meal) {
        Map<Integer,Meal> meals = repository.computeIfAbsent(userId, m -> new ConcurrentHashMap<Integer, Meal>(m));
        if (meal.isNew() ) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        Map<Integer,Meal> meals = repository.get(userId);
        if (meals!=null)
            return meals.remove(id) != null;
        return false;
    }

    @Override
    public Meal get(int userId, int id) {
        Map<Integer,Meal> meals = repository.get(userId);
        if (meals!=null &&meals.containsKey(id))
            return meals.get(id);
        else
            return null;

    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAllCommon(userId,m->true);
    }

    @Override
    public List<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        return getAllCommon(userId,d -> DateTimeUtil.isBetween(d.getDate(), startDate, endDate));

    }

    public List<Meal> getAllCommon(int userId, Predicate<Meal> predicate){
        Map<Integer,Meal> meals = repository.get(userId);
        return meals.values()
                .stream()
                .filter(predicate)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

