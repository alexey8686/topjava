package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@Profile("postgres")
public class JdbcMealPostgresRepositoryImpl extends AbstractJdbcMealRepository<LocalDateTime> {

    @Override
    public LocalDateTime getDate(LocalDateTime value) {
        return value;
    }
}
