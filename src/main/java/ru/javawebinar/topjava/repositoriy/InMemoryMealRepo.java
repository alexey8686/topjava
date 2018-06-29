package ru.javawebinar.topjava.repositoriy;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepo implements MealRepositoriyInterface {
    Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();
    AtomicInteger atomicId = new AtomicInteger(0);

    public InMemoryMealRepo() {
        mealMap.put(atomicId.incrementAndGet(), new Meal(atomicId.get(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        mealMap.put(atomicId.incrementAndGet(), new Meal(atomicId.get(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        mealMap.put(atomicId.incrementAndGet(), new Meal(atomicId.get(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        mealMap.put(atomicId.incrementAndGet(), new Meal(atomicId.get(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        mealMap.put(atomicId.incrementAndGet(), new Meal(atomicId.get(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        mealMap.put(atomicId.incrementAndGet(), new Meal(atomicId.get(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public List<Meal> getAll() {
        return mealMap.values().stream().collect(Collectors.toList());
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
        if (meal.isNew(meal.getId())) {
            mealMap.put(atomicId.incrementAndGet(), new Meal(atomicId.get(), meal.getDateTime(), meal.getDescription(), meal.getCalories()));
            meal.setId(atomicId.get());
        } else {
            mealMap.put(meal.getId(), meal);
        }
        return meal;
    }
}
