package ru.javawebinar.topjava.repositoriy;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InMemoryMealRepo implements MealRepositoriyInterface {
    private Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();
    private AtomicInteger atomicId = new AtomicInteger(0);

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
        if (meal.isNew(meal.getId())) {
            meal.setId(atomicId.incrementAndGet());
            mealMap.put(atomicId.get(), meal);

        } else {
            mealMap.put(meal.getId(), meal);
        }
        return meal;
    }
}
