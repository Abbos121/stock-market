package com.vention.stockmarket.repository;

import com.vention.stockmarket.domain.SecurityModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface BaseRepository<T> {
    String DB_URL = "jdbc:postgresql://localhost:5432/stock-market";
    String DB_USERNAME = "postgres";
    String DB_PASSWORD = "6040";

    Long create(T t);
    T getById(Long id);
    void update(T t);
    void delete(Long id);

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}
