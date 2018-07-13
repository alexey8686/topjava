package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepositoryImpl implements MealRepository {

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = new BeanPropertyRowMapper<>(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcMealRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    @Override
    public Meal save(Meal meal, int userId) {
        String sql = "UPDATE meals SET  date_time=?, description=?, calories=? WHERE id=? AND user_id=?";
        Object[] params = {meal.getDateTime(), meal.getDescription(), meal.getCalories(), meal.getId(), userId};

        if (meal.isNew()) {
            String INSERT_SQL = "INSERT INTO meals (date_time, description, calories, user_id) VALUES(?,?,?,?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(
                    new PreparedStatementCreator() {
                        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[]{"id"});
                            ps.setObject(1, meal.getDateTime());
                            ps.setString(2, meal.getDescription());
                            ps.setInt(3, meal.getCalories());
                            ps.setInt(4, userId);
                            return ps;
                        }
                    },
                    keyHolder);

            meal.setId(keyHolder.getKey().intValue());
        } else if (jdbcTemplate.update(sql, params) == 0)
            return null;

        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE  FROM meals WHERE id=? AND user_id = ? ", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = jdbcTemplate.query("SELECT * FROM meals WHERE id=? AND user_id=?", ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE  user_id=? ORDER BY date_time DESC ", ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query("SELECT * FROM meals WHERE  user_id=? "
                + " AND date_time  BETWEEN  ? AND ? ORDER BY date_time DESC ", ROW_MAPPER, userId, startDate, endDate);
    }
}
