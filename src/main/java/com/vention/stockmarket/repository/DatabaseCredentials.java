package com.vention.stockmarket.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseCredentials {
    @Value("${spring.datasource.url}")
    public String DB_URL;
    @Value("${spring.datasource.username}")
    public String DB_USERNAME;
    @Value("${spring.datasource.password}")
    public String DB_PASSWORD;

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}
