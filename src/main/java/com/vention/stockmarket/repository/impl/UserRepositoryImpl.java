package com.vention.stockmarket.repository.impl;

import com.vention.stockmarket.domain.UserModel;
import com.vention.stockmarket.dto.request.UserRegisterDTO;
import com.vention.stockmarket.enumuration.Role;
import com.vention.stockmarket.exceptions.CustomResourceNotFoundException;
import com.vention.stockmarket.repository.DatabaseCredentials;
import com.vention.stockmarket.repository.UserRepository;
import com.vention.stockmarket.utils.DateUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DatabaseCredentials databaseCredentials;

    @Override
    public Optional<Long> create(UserModel user) {
        String sql = "INSERT INTO users (first_name, second_name, date_of_birth, created_at) VALUES (?, ?, ?, ?)";

        try (Connection connection = databaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            preparedStatement.setDate(3, DateUtils.convertUtilDateToSqlDate(user.getDateOfBirth()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(user.getCreatedAt()));

            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return Optional.of(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<UserModel> getById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";

        try (Connection connection = databaseCredentials.getConnection();
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
                    var updatedAt = resultSet.getTimestamp("updated_at");
                    if (updatedAt != null)
                        user.setUpdatedAt(updatedAt.toLocalDateTime());
                    return Optional.of(user);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public Optional<Long> registerUser(UserRegisterDTO registerDTO) {
        String userTableSql = "INSERT INTO users (first_name, second_name, date_of_birth, created_at) VALUES (?, ?, ?, ?)";
        String securityTableSql = "INSERT INTO security_credentials (user_id, email, password, roles) VALUES (?, ?, ?, ?)";

        try (Connection connection = databaseCredentials.getConnection();
             PreparedStatement preparedStatementForUser = connection.prepareStatement(userTableSql, PreparedStatement.RETURN_GENERATED_KEYS);
             PreparedStatement preparedStatementForSecurity = connection.prepareStatement(securityTableSql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            connection.setAutoCommit(false);
            preparedStatementForUser.setString(1, registerDTO.getFirstName());
            preparedStatementForUser.setString(2, registerDTO.getSecondName());
            var dateOfBirth = DateUtils.convertStringToDate(registerDTO.getDateOfBirth());
            preparedStatementForUser.setDate(3, DateUtils.convertUtilDateToSqlDate(dateOfBirth));
            preparedStatementForUser.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatementForUser.executeUpdate();

            try (ResultSet generatedKeys = preparedStatementForUser.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long userId = generatedKeys.getLong(1);
                    preparedStatementForSecurity.setLong(1, userId);
                    preparedStatementForSecurity.setString(2, registerDTO.getEmail());
                    preparedStatementForSecurity.setString(3, registerDTO.getPassword());
                    preparedStatementForSecurity.setString(4, Set.of(Role.USER).toString());
                    preparedStatementForSecurity.executeUpdate();
                    connection.commit();
                    return Optional.of(userId);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void update(UserModel user) {
        String sql = "UPDATE users SET first_name = ?, second_name = ?, date_of_birth = ?, updated_at = ? WHERE id = ?";

        try (Connection connection = databaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getSecondName());
            if (user.getDateOfBirth() != null) {
                preparedStatement.setDate(3, DateUtils.convertUtilDateToSqlDate(user.getDateOfBirth()));
            }
            preparedStatement.setTimestamp(4, Timestamp.valueOf(user.getUpdatedAt()));
            preparedStatement.setLong(5, user.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating user failed, no rows affected.");
            }
        } catch (SQLException e) {
            log.error("" + e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection connection = databaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }


    @Override
    public List<UserModel> getAll() {
        String sql = "SELECT * FROM users";
        List<UserModel> users = new ArrayList<>();

        try (Connection connection = databaseCredentials.getConnection();
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
            log.error(e.getMessage());
        }
        return users;
    }

    @Override
    public Optional<UserModel> getByUserId(Long id) {
        String sql = "SELECT * FROM users where id = ?";

        try (Connection connection = databaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                UserModel user = new UserModel();
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setSecondName(resultSet.getString("second_name"));
                user.setDateOfBirth(resultSet.getDate("date_of_birth"));
                user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                user.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                return Optional.of(user);
            } else {
                throw new CustomResourceNotFoundException("user not found with id : " + id);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return Optional.empty();
    }


}
