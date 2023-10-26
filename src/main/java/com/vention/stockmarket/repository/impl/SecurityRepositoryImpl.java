package com.vention.stockmarket.repository.impl;

import com.vention.stockmarket.domain.SecurityCredentials;
import com.vention.stockmarket.enumuration.Role;
import com.vention.stockmarket.exceptions.CustomResourceNotFoundException;
import com.vention.stockmarket.repository.DatabaseCredentials;
import com.vention.stockmarket.repository.SecurityRepository;
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

@Slf4j
@Repository
@RequiredArgsConstructor
public class SecurityRepositoryImpl implements SecurityRepository {
    private final DatabaseCredentials databaseCredentials;

    @Override
    public Optional<Long> create(SecurityCredentials security) {
        String sql = "INSERT INTO security_credentials (user_id, email, password, roles) VALUES (?, ?, ?, ?)";

        try (Connection connection = databaseCredentials.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, security.getUserId());
            preparedStatement.setString(2, security.getEmail());
            preparedStatement.setString(3, security.getPassword());
            preparedStatement.setString(4, security.getRoles().toString());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return Optional.of(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating security failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<SecurityCredentials> getById(Long id) {
        String sql = "SELECT * FROM security_credentials WHERE id = ?";

        try (Connection connection = databaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    SecurityCredentials security = new SecurityCredentials();
                    security.setId(resultSet.getLong("id"));
                    security.setUserId(resultSet.getLong("user_id"));
                    security.setEmail(resultSet.getString("email"));
                    security.setPassword(resultSet.getString("password"));
                    var roles = Role.convertFromStringToSet(resultSet.getString("roles"));
                    security.setRoles(roles);
                    return Optional.of(security);
                }
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void update(SecurityCredentials security) {
        String sql = "UPDATE security_credentials SET user_id = ?, email = ?, password = ?, roles = ?, updated_at = ? WHERE id = ?";

        try (Connection connection = databaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, security.getUserId());
            preparedStatement.setString(2, security.getEmail());
            preparedStatement.setString(3, security.getPassword());
            preparedStatement.setString(4, security.getRoles().toString());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setLong(6, security.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating security failed, no rows affected.");
            }

        } catch (SQLException e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM security_credentials WHERE id = ?";

        try (Connection connection = databaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting security failed, no rows affected.");
            }

        } catch (SQLException e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public List<SecurityCredentials> getAll() {
        String sql = "SELECT * FROM security_credentials";
        List<SecurityCredentials> securities = new ArrayList<>();

        try (Connection connection = databaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                SecurityCredentials security = new SecurityCredentials();
                security.setId(resultSet.getLong("id"));
                security.setUserId(resultSet.getLong("user_id"));
                security.setEmail(resultSet.getString("email"));
                security.setPassword(resultSet.getString("password"));
                var roles = Role.convertFromStringToSet(resultSet.getString("roles"));
                security.setRoles(roles);
                securities.add(security);
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
        return securities;
    }

    @Override
    public Optional<SecurityCredentials> getByEmail(String email) {
        String sql = "SELECT * FROM security_credentials where email = ?";
        try (Connection connection = databaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                SecurityCredentials security = new SecurityCredentials();
                security.setId(resultSet.getLong("id"));
                security.setUserId(resultSet.getLong("user_id"));
                security.setEmail(resultSet.getString("email"));
                security.setPassword(resultSet.getString("password"));
                var roles = Role.convertFromStringToSet(resultSet.getString("roles"));
                security.setRoles(roles);
                return Optional.of(security);
            } else {
                throw new CustomResourceNotFoundException(email + "email not found");
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<SecurityCredentials> getByUserId(Long userId) {
        String sql = "SELECT * FROM security_credentials where user_id = ?";
        try (Connection connection = databaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                SecurityCredentials security = new SecurityCredentials();
                security.setId(resultSet.getLong("id"));
                security.setUserId(resultSet.getLong("user_id"));
                security.setEmail(resultSet.getString("email"));
                security.setPassword(resultSet.getString("password"));
                var roles = Role.convertFromStringToSet(resultSet.getString("roles"));
                security.setRoles(roles);
                return Optional.of(security);
            } else {
                throw new CustomResourceNotFoundException(userId + "email not found");
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
            return Optional.empty();
        }
    }
}
