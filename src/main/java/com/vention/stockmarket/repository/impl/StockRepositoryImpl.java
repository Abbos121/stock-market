package com.vention.stockmarket.repository.impl;

import com.vention.stockmarket.domain.StockModel;
import com.vention.stockmarket.exceptions.CustomResourceNotFoundException;
import com.vention.stockmarket.repository.DatabaseCredentials;
import com.vention.stockmarket.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepository {
    private final DatabaseCredentials databaseCredentials;

    @Override
    public void saveAllStocks(List<StockModel> stocks) {
        String sql = "INSERT INTO stocks (symbol, name, currency, exchange, mix_code, country, price, type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ";

        try (Connection connection = databaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (StockModel stock : stocks) {
                preparedStatement.setString(1, stock.getSymbol());
                preparedStatement.setString(2, stock.getName());
                preparedStatement.setString(3, stock.getCurrency());
                preparedStatement.setString(4, stock.getExchange());
                preparedStatement.setString(5, stock.getMixCode());
                preparedStatement.setString(6, stock.getCountry());
                preparedStatement.setDouble(7, stock.getPrice());
                preparedStatement.setString(8, stock.getType());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public List<StockModel> findAll() {
        String sql = "SELECT * FROM stocks";
        List<StockModel> stocks = new ArrayList<>();

        try (Connection connection = databaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                StockModel stock = new StockModel();
                stock.setId(resultSet.getLong("id"));
                stock.setSymbol(resultSet.getString("symbol"));
                stock.setName(resultSet.getString("name"));
                stock.setCurrency(resultSet.getString("currency"));
                stock.setExchange(resultSet.getString("exchange"));
                stock.setMixCode(resultSet.getString("mix_code"));
                stock.setCountry(resultSet.getString("country"));
                stock.setType(resultSet.getString("type"));
                stock.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                stocks.add(stock);
            }
        } catch (SQLException e) {
            log.error(e.getMessage()); // Handle the exception appropriately
        }

        return stocks;
    }

    @Override
    public List<StockModel> findAll(List<String> companySymbols) {
        String sql = createSqlUsingIn("SELECT * FROM stocks where symbol in (?)", companySymbols);

        List<StockModel> stocks = new ArrayList<>();

        try (Connection connection = databaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                StockModel stock = new StockModel();
                stock.setId(resultSet.getLong("id"));
                stock.setSymbol(resultSet.getString("symbol"));
                stock.setName(resultSet.getString("name"));
                stock.setCurrency(resultSet.getString("currency"));
                stock.setExchange(resultSet.getString("exchange"));
                stock.setMixCode(resultSet.getString("mix_code"));
                stock.setCountry(resultSet.getString("country"));
                stock.setType(resultSet.getString("type"));
                stock.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                stocks.add(stock);
            }
        } catch (SQLException e) {
            log.error(e.getMessage()); // Handle the exception appropriately
        }

        return stocks;
    }


    @Override
    public Optional<StockModel> findBySymbol(String symbol) {
        String sql = "SELECT * FROM stocks where symbol = ?";

        try (Connection connection = databaseCredentials.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, symbol);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                StockModel stock = new StockModel();
                stock.setId(resultSet.getLong("id"));
                stock.setSymbol(resultSet.getString("symbol"));
                stock.setName(resultSet.getString("name"));
                stock.setCurrency(resultSet.getString("currency"));
                stock.setExchange(resultSet.getString("exchange"));
                stock.setMixCode(resultSet.getString("mix_code"));
                stock.setCountry(resultSet.getString("country"));
                stock.setType(resultSet.getString("type"));
                stock.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                return Optional.of(stock);
            } else {
                throw new CustomResourceNotFoundException("stock not found with symbol : " + symbol);
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }

    private String createSqlUsingIn(String sql, List<String> symbols) {
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < symbols.size(); i++) {
            placeholders.append("'" + symbols.get(i) + "'");
            if (i != symbols.size() - 1) {
                placeholders.append(", ");
            }
        }
        return sql.replace("?", placeholders);
    }


}