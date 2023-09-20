package com.vention.stockmarket.repository.impl;

import com.vention.stockmarket.model.SecurityModel;
import com.vention.stockmarket.repository.SecurityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SecurityRepositoryImpl implements SecurityRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Long create(SecurityModel security) {
        String sql = "INSERT INTO security (user_id, email, password) VALUES (?, ?, ?) RETURNING id";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                connection -> {
                    PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setLong(1, security.getUserId());
                    ps.setString(2, security.getEmail());
                    ps.setString(3, security.getPassword());
                    return ps;
                },
                keyHolder
        );

        return keyHolder.getKey().longValue();
    }

    @Override
    public SecurityModel getById(Long id) {
        String sql = "SELECT * FROM security WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(SecurityModel.class), id);
    }

    @Override
    public void update(SecurityModel security) {
        String sql = "UPDATE security SET user_id = ?, email = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql, security.getUserId(), security.getEmail(), security.getPassword(), security.getId());
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM security WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<SecurityModel> getAll() {
        String sql = "SELECT * FROM security";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(SecurityModel.class));
    }
}
