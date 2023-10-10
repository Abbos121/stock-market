package com.vention.stockmarket.repository;

import com.vention.stockmarket.domain.UserModel;
import com.vention.stockmarket.dto.request.UserRegisterDTO;

import java.util.List;

public interface UserRepository extends BaseRepository<UserModel> {
    Long registerUser(UserRegisterDTO registerDTO);
    List<UserModel> getAll();

    UserModel getByUserId(Long id);
}
