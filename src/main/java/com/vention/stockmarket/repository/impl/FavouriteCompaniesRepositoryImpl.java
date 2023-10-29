package com.vention.stockmarket.repository.impl;

import com.vention.stockmarket.exceptions.CustomResourceAlreadyExistException;
import com.vention.stockmarket.exceptions.CustomResourceNotFoundException;
import com.vention.stockmarket.repository.DatabaseCredentials;
import com.vention.stockmarket.repository.FavouriteCompaniesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FavouriteCompaniesRepositoryImpl implements FavouriteCompaniesRepository {
    private final DatabaseCredentials databaseCredentials;

    @Override
    public void add(Long userId, String companySymbol) {
        if (existsByUserIdAndCompanySymbol(userId, companySymbol)) {
            throw new CustomResourceAlreadyExistException("resource already exists with company-id : " + companySymbol);
        }
        String sql = "insert into favourite_companies (user_id, company_symbol) values (?, ?)";
        try (Connection connection = databaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, companySymbol);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public void delete(Long userId, String companySymbol) {
        if (!existsByUserIdAndCompanySymbol(userId, companySymbol)) {
            throw new CustomResourceNotFoundException("resource not found with company-symbol : " + companySymbol);
        }
        String sql = "delete from favourite_companies where user_id = ? and company_symbol = ?";
        try (Connection connection = databaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, companySymbol);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public List<String> findByUserId(Long userId) {
        String sql = "select company_symbol from favourite_companies where user_id = ?";
        List<String> companiesIdList = new ArrayList<>();
        try (Connection connection = databaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                companiesIdList.add(resultSet.getString("company_symbol"));
            }
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
        return companiesIdList;
    }

    @Override
    public boolean existsByUserIdAndCompanySymbol(Long userId, String companySymbol) {
        String sql = "select * from favourite_companies where user_id = ? and company_symbol = ?";

        try (Connection connection = databaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, userId);
            preparedStatement.setString(2, companySymbol);
            var resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            log.info(e.getMessage());
            return false;
        }
    }
}
