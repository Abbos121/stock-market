package com.vention.stockmarket.repository;

import com.vention.stockmarket.model.SecurityModel;
import com.vention.stockmarket.model.UserModel;

import java.util.List;

public interface SecurityRepository {
    Long create(SecurityModel security);
    SecurityModel getById(Long id);
    void update(SecurityModel security);
    void delete(Long id);
    List<SecurityModel> getAll();
}
