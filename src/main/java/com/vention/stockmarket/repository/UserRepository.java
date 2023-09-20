package com.vention.stockmarket.repository;

import com.vention.stockmarket.model.SecurityModel;
import com.vention.stockmarket.model.UserModel;

import java.util.List;

public interface UserRepository {
    Long create(UserModel user);
    UserModel getById(Long id);
    void update(UserModel user);
    void delete(Long id);
    List<UserModel> getAll();
}
