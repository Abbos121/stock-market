package com.vention.stockmarket.repository;

import com.vention.stockmarket.domain.UserModel;

import java.util.List;

public interface UserRepository extends BaseRepository<UserModel> {
    List<UserModel> getAll();
}
