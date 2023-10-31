package com.vention.stockmarket.repository.impl.mapper;

import com.vention.stockmarket.domain.StockModel;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockModelMapper {
    public static StockModel mapRow(ResultSet resultSet) throws SQLException {
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
    }
}