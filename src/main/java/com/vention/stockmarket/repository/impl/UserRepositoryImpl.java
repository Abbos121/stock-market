package com.vention.stockmarket.repository.impl;

import com.vention.stockmarket.model.UserModel;
import com.vention.stockmarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long create(UserModel user) {
        String sql = "INSERT INTO users (first_name, second_name, date_of_birth, created_at) " +
                "VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, user.getFirstName());
                    ps.setString(2, user.getSecondName());
                    ps.setDate(3, user.getDateOfBirth());
                    ps.setTimestamp(4, Timestamp.valueOf(user.getCreatedAt()));
                    return ps;
                },
                keyHolder
        );
        return keyHolder.getKey().longValue();
    }

    @Override
    public UserModel getById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) -> {
            UserModel user = new UserModel();
            user.setId(rs.getLong("id"));
            user.setFirstName(rs.getString("first_name"));
            user.setSecondName(rs.getString("second_name"));
            user.setDateOfBirth(rs.getDate("date_of_birth"));
            user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
            return user;
        });
    }

    @Override
    public void update(UserModel user) {
        String sql = "UPDATE users SET first_name = ?, second_name = ?, date_of_birth = ?, " +
                "updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getFirstName(), user.getSecondName(),
                user.getDateOfBirth(), user.getUpdatedAt(), user.getId());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<UserModel> getAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(UserModel.class));
    }
}
