package ru.javawebinar.topjava.repository.jpa;

import org.hibernate.Criteria;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User ref = em.getReference(User.class, userId);
        meal.setUser(ref);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else if (get(meal.getId(), userId) != null) {
            return em.merge(meal);
        } else {
            return null;
        }


    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createQuery("DELETE FROM Meal m WHERE m.id=:id and m.user.id=:userId")
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meal = em.createQuery("SELECT m FROM Meal m where m.id=:id and m.user.id=:userId")
                .setParameter("id", id)
                .setParameter("userId", userId)
                .getResultList();
        return DataAccessUtils.singleResult(meal);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createQuery("SELECT m FROM Meal m where m.user.id=:userId ORDER BY m.dateTime DESC")
                .setParameter("userId", userId)
                .getResultList();

    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createQuery("SELECT m FROM Meal m WHERE m.user.id=?1 AND m.dateTime BETWEEN ?2 AND ?3 ORDER BY m.dateTime DESC")
                .setParameter(1, userId)
                .setParameter(2, startDate)
                .setParameter(3, endDate)
                .getResultList();
    }
}