package com.vention.stockmarket.repository.impl.mapper;

import com.vention.stockmarket.domain.SecurityCredentials;
import com.vention.stockmarket.enumuration.Role;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SecurityCredentialsMapper {
    public static SecurityCredentials mapRow(ResultSet resultSet) throws SQLException {
        SecurityCredentials security = new SecurityCredentials();
        security.setId(resultSet.getLong("id"));
        security.setUserId(resultSet.getLong("user_id"));
        security.setEmail(resultSet.getString("email"));
        security.setPassword(resultSet.getString("password"));
        var roles = Role.convertFromStringToSet(resultSet.getString("roles"));
        security.setRoles(roles);
        return security;
    }
}