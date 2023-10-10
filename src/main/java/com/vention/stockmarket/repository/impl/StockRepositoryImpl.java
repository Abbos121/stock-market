package com.vention.stockmarket.repository.impl;

import com.vention.stockmarket.domain.StockModel;
import com.vention.stockmarket.exceptions.ResourceNotFoundException;
import com.vention.stockmarket.repository.StockRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.vention.stockmarket.repository.BaseRepository.DB_PASSWORD;
import static com.vention.stockmarket.repository.BaseRepository.DB_URL;
import static com.vention.stockmarket.repository.BaseRepository.DB_USERNAME;

@Repository
public class StockRepositoryImpl implements StockRepository {

    @Override
    public void saveAllStocks(List<StockModel> stocks) {
        String sql = "INSERT INTO stocks (symbol, name, currency, exchange, mix_code, country, type) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (symbol) " +
                "DO UPDATE SET " +
                "name = EXCLUDED.name, " +
                "currency = EXCLUDED.currency, " +
                "exchange = EXCLUDED.exchange, " +
                "mix_code = EXCLUDED.mix_code, " +
                "country = EXCLUDED.country, " +
                "type = EXCLUDED.type, " +
                "updated_at = now()";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            for (StockModel stock : stocks) {
                preparedStatement.setString(1, stock.getSymbol());
                preparedStatement.setString(2, stock.getName());
                preparedStatement.setString(3, stock.getCurrency());
                preparedStatement.setString(4, stock.getExchange());
                preparedStatement.setString(5, stock.getMixCode());
                preparedStatement.setString(6, stock.getCountry());
                preparedStatement.setString(7, stock.getType());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<StockModel> findAll() {
        String sql = "SELECT * FROM stocks";
        List<StockModel> stocks = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
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
            e.printStackTrace(); // Handle the exception appropriately
        }

        return stocks;
    }

    @Override
    public List<StockModel> findAll(List<Long> idList) {
        String sql = createSqlUsingIn("SELECT * FROM stocks where id in (?)", idList);

        List<StockModel> stocks = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
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
            e.printStackTrace(); // Handle the exception appropriately
        }

        return stocks;
    }


    @Override
    public StockModel findBySymbol(String symbol) {
        String sql = "SELECT * FROM stocks where symbol = ?";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
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
                return stock;
            } else {
                throw new ResourceNotFoundException("stock not found with symbol : " + symbol);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
            throw new RuntimeException(e);
        }
    }

    private String createSqlUsingIn(String sql, List<Long> idList) {
        StringBuilder placeholders = new StringBuilder();
        for (int i = 0; i < idList.size(); i++) {
            placeholders.append(idList.get(i));
            if (i != idList.size() - 1) {
                placeholders.append(", ");
            }
        }
        return sql.replace("?", placeholders);
    }


}