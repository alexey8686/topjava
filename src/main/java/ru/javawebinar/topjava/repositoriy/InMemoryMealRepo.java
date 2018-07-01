package ru.javawebinar.topjava.repositoriy;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepo implements MealRepositoriyInterface {
    private Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();

    private  AtomicInteger atomicId = new AtomicInteger(0);

    private static final List<Meal> MEALS = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510),
            new Meal(LocalDateTime.of(2015, Month.MAY, 29, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 29, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 29, 20, 0), "Ужин", 510)
            );

    public InMemoryMealRepo (){
        MEALS.forEach(this::save);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }

    @Override
    public void delete(int id) {
        mealMap.remove(id);

    }

    @Override
    public Meal get(Integer id) {
        return mealMap.get(id);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(atomicId.incrementAndGet());
            mealMap.put(meal.getId(), meal);
            return meal;
        }

         return   mealMap.computeIfPresent(meal.getId(),(id,meals)->meal);
    }

}
