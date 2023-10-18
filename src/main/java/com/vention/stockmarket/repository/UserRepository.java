package com.vention.stockmarket.repository;

import com.vention.stockmarket.domain.UserModel;
import com.vention.stockmarket.dto.request.UserRegisterDTO;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends BaseRepository<UserModel> {
    Optional<Long> registerUser(UserRegisterDTO registerDTO);
    List<UserModel> getAll();

    Optional<UserModel> getByUserId(Long id);
}
