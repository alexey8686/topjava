package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.Optional;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {


   /* List<Meal> findAllByUser_IdOrderByDateTime(int userId);

    int deleteByIdAndUser_Id (int id, int userId);*/

   @Modifying
   @Query("SELECT m FROM Meal m where m.user.id =?1")
   List<Meal> getAll (int userId);

    @Override
    Meal getOne(Integer integer);

    @Override
    Meal save(Meal meal);

    @Modifying
    @Query("SELECT m FROM Meal m WHERE m.id=?1 AND m.user.id=?2")
    Optional <Meal> getByIdAndUserId (int id,int userId);
}
