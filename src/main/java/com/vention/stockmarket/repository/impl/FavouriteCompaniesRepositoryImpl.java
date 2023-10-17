package com.vention.stockmarket.repository.impl;

import com.vention.stockmarket.exceptions.CustomResourceNotFoundException;
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
    public void add(Long userId, String companySymbol) {
        if (existsByUserIdAndCompanySymbol(userId, companySymbol)) {
            throw new CustomResourceNotFoundException("resource already exists with company-id : " + companySymbol);
        }
        String sql = "insert into favourite_companies (user_id, company_symbol) values (?, ?)";
        try (Connection connection = DatabaseCredentials.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, companySymbol);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void delete(Long userId, String companySymbol) {
        if (!existsByUserIdAndCompanySymbol(userId, companySymbol)) {
            throw new CustomResourceNotFoundException("resource not found with company-symbol : " + companySymbol);
        }
        String sql = "delete from favourite_companies where user_id = ? and company_symbol = ?";
        try (Connection connection = DatabaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, companySymbol);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public List<Long> findByUserId(Long userId) {
        String sql = "select company_symbol from favourite_companies where user_id = ?";
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
    public boolean existsByUserIdAndCompanySymbol(Long userId, String companySymbol) {
        String sql = "select * from favourite_companies where user_id = ? and company_symbol = ?";

        try(Connection connection = DatabaseCredentials.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, companySymbol);
            var resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new CustomSQLException();
        }
    }
}
