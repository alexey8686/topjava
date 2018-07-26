package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {


    List<Meal> findAllByUser_IdOrderByDateTimeDesc(int userId);

    @Transactional
    int deleteByIdAndUser_Id (int id, int userId);

    @Transactional
    @Override
    Meal save(Meal meal);


    Optional <Meal> getByIdAndUser_Id (int id,int userId);

    @Modifying
    @Query("SELECT m FROM Meal m where m.user.id=?1 AND m.dateTime BETWEEN ?2 AND ?3 ORDER BY m.dateTime DESC ")
    List<Meal> getBetween(int userId, LocalDateTime srartDate,LocalDateTime endDate);


}
