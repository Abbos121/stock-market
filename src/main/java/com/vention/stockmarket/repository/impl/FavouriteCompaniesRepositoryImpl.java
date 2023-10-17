package com.vention.stockmarket.repository.impl;

import com.vention.stockmarket.exceptions.CustomSQLException;
import com.vention.stockmarket.repository.DatabaseCredentials;
import com.vention.stockmarket.repository.FavouriteCompaniesRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class FavouriteCompaniesRepositoryImpl implements FavouriteCompaniesRepository {

    @Override
    public void add(Long userId, Long companyId) {
        if (existsByUserIdAndCompanyId(userId, companyId)) {
            throw new RuntimeException("resource already exists with company-id : " + companyId);
        }
        String sql = "insert into favourite_companies (user_id, company_id) values (?, ?)";
        try (Connection connection = DatabaseCredentials.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, companyId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void delete(Long userId, Long companyId) {
        if (!existsByUserIdAndCompanyId(userId, companyId)) {
            throw new RuntimeException("resource not found with company-id : " + companyId);
        }
        String sql = "delete from favourite_companies where user_id = ? and company_id = ?";
        try (Connection connection = DatabaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, companyId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public List<Long> findByUserId(Long userId) {
        String sql = "select company_id from favourite_companies where user_id = ?";
        List<Long> companiesIdList = new ArrayList<>();
        try(Connection connection = DatabaseCredentials.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                companiesIdList.add(resultSet.getLong("company_id"));
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        return companiesIdList;
    }

    @Override
    public boolean existsByUserIdAndCompanyId(Long userId, Long companyId) {
        String sql = "select * from favourite_companies where user_id = ? and company_id = ?";

        try(Connection connection = DatabaseCredentials.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, companyId);
            var resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new CustomSQLException();
        }
    }
}
