package com.vention.stockmarket.repository.impl;

import com.vention.stockmarket.domain.UserModel;
import com.vention.stockmarket.repository.BaseRepository;
import com.vention.stockmarket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    
    @Override
    public Long create(UserModel user) {
        String sql = "INSERT INTO users (first_name, second_name, date_of_birth, created_at) VALUES (?, ?, ?, ?)";

        try (Connection connection = BaseRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setDate(3, user.getDateOfBirth());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(user.getCreatedAt()));

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            // Handle any exceptions here
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserModel getById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = BaseRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    UserModel user = new UserModel();
                    user.setId(resultSet.getLong("id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setSecondName(resultSet.getString("second_name"));
                    user.setDateOfBirth(resultSet.getDate("date_of_birth"));
                    user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                    user.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                    return user;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(UserModel user) {
        String sql = "UPDATE users SET first_name = ?, second_name = ?, date_of_birth = ?, updated_at = ? WHERE id = ?";

        try (Connection connection = BaseRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setDate(3, user.getDateOfBirth());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(user.getUpdatedAt()));
            preparedStatement.setLong(5, user.getId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0)
                throw new SQLException("Updating user failed, no rows affected.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = BaseRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0)
                throw new SQLException("Deleting user failed, no rows affected.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<UserModel> getAll() {
        String sql = "SELECT * FROM users";
        List<UserModel> users = new ArrayList<>();

        try (Connection connection = BaseRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                UserModel user = new UserModel();
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setSecondName(resultSet.getString("second_name"));
                user.setDateOfBirth(resultSet.getDate("date_of_birth"));
                user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                user.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return users;
    }
}
