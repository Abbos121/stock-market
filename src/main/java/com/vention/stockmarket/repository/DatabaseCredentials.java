package com.vention.stockmarket.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface DatabaseCredentials {
    String DB_URL = "jdbc:postgresql://localhost:5432/stock-market";
    String DB_USERNAME = "postgres";
    String DB_PASSWORD = "6040";

    static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}
