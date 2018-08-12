package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else {
            if (namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay  WHERE id=:id"
                    , parameterSource) == 0) {
                return null;
            }
            jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
        }
        insertRole(user);
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return setRole(DataAccessUtils.singleResult(users)
        );
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return setRole(DataAccessUtils.singleResult(users));
    }

    @Override
    public List<User> getAll() {
        Map<Integer, Set<Role>> roleMap = new HashMap<>();
        jdbcTemplate.query("SELECT * FROM user_roles",
                rs -> {
                    roleMap.computeIfAbsent(rs.getInt("user_id"),
                            id -> EnumSet.noneOf(Role.class))
                            .add(Role.valueOf(rs.getString("role")));
                });
        List<User> users = jdbcTemplate.query("SELECT * FROM users  ORDER BY name, email", ROW_MAPPER);
        users.forEach(u -> u.setRoles(roleMap.get(u.getId())));
        return users;
    }

    public User setRole(User user) {
        if (user != null) {
            List<Role> roles = jdbcTemplate.query("SELECT * FROM user_roles WHERE user_id=?"
                    , (rs, rowNum) -> Role.valueOf(rs.getString("role")), user.getId());
            user.setRoles(roles);
        }
        return user;
    }

    public void insertRole(User user) {
        Set<Role> roleSet = user.getRoles();

        class setParam implements ParameterizedPreparedStatementSetter<Role> {

            @Override
            public void setValues(PreparedStatement preparedStatement, Role role) throws SQLException {
                preparedStatement.setInt(1, user.getId());
                preparedStatement.setString(2, role.name());
            }
        }
        jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id,role) VALUES (?, ?)", roleSet, roleSet.size(), new setParam());
    }
}
