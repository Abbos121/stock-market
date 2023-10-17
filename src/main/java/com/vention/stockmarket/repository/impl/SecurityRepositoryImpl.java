package com.vention.stockmarket.repository.impl;

import com.vention.stockmarket.domain.SecurityModel;
import com.vention.stockmarket.enumuration.Role;
import com.vention.stockmarket.exceptions.CustomResourceNotFoundException;
import com.vention.stockmarket.exceptions.CustomSQLException;
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

@Slf4j
@Repository
@RequiredArgsConstructor
public class SecurityRepositoryImpl implements SecurityRepository {

    @Override
    public Long create(SecurityModel security) {
        String sql = "INSERT INTO security (user_id, email, password, roles) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseCredentials.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setLong(1, security.getUserId());
            preparedStatement.setString(2, security.getEmail());
            preparedStatement.setString(3, security.getPassword());
            preparedStatement.setString(4, security.getRoles().toString());
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                } else {
                    throw new SQLException("Creating security failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new CustomSQLException();
        }
    }

    @Override
    public SecurityModel getById(Long id) {
        String sql = "SELECT * FROM security WHERE id = ?";

        try (Connection connection = DatabaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    SecurityModel security = new SecurityModel();
                    security.setId(resultSet.getLong("id"));
                    security.setUserId(resultSet.getLong("user_id"));
                    security.setEmail(resultSet.getString("email"));
                    security.setPassword(resultSet.getString("password"));
                    var roles = Role.convertFromStringToSet(resultSet.getString("roles"));
                    security.setRoles(roles);
                    return security;
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new CustomSQLException();
        }
    }

    @Override
    public void update(SecurityModel security) {
        String sql = "UPDATE security SET user_id = ?, email = ?, password = ?, roles = ?, updated_at = ? WHERE id = ?";

        try (Connection connection = DatabaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, security.getUserId());
            preparedStatement.setString(2, security.getEmail());
            preparedStatement.setString(3, security.getPassword());
            preparedStatement.setString(4, security.getRoles().toString());
            preparedStatement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatement.setLong(6, security.getId());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0)
                throw new SQLException("Updating security failed, no rows affected.");

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM security WHERE id = ?";

        try (Connection connection = DatabaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0)
                throw new SQLException("Deleting security failed, no rows affected.");

        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public List<SecurityModel> getAll() {
        String sql = "SELECT * FROM security";
        List<SecurityModel> securities = new ArrayList<>();

        try (Connection connection = DatabaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                SecurityModel security = new SecurityModel();
                security.setId(resultSet.getLong("id"));
                security.setUserId(resultSet.getLong("user_id"));
                security.setEmail(resultSet.getString("email"));
                security.setPassword(resultSet.getString("password"));
                var roles = Role.convertFromStringToSet(resultSet.getString("roles"));
                security.setRoles(roles);
                securities.add(security);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return securities;
    }

    @Override
    public SecurityModel getByEmail(String email) {
        String sql = "SELECT * FROM security where email = ?";
        try (Connection connection = DatabaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                SecurityModel security = new SecurityModel();
                security.setId(resultSet.getLong("id"));
                security.setUserId(resultSet.getLong("user_id"));
                security.setEmail(resultSet.getString("email"));
                security.setPassword(resultSet.getString("password"));
                var roles = Role.convertFromStringToSet(resultSet.getString("roles"));
                security.setRoles(roles);
                return security;
            } else {
                throw new CustomResourceNotFoundException(email + "email not found");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new CustomSQLException();
        }
    }


}
