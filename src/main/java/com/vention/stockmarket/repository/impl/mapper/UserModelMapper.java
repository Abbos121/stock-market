package com.vention.stockmarket.repository.impl.mapper;

import com.vention.stockmarket.domain.UserModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserModelMapper {
    public static UserModel mapRow(ResultSet resultSet) throws SQLException {
        UserModel user = new UserModel();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setSecondName(resultSet.getString("second_name"));
        user.setDateOfBirth(resultSet.getDate("date_of_birth"));
        user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
        Timestamp updatedAt = resultSet.getTimestamp("updated_at");
        if (updatedAt != null) {
            user.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        return user;
    }
}