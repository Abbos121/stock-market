package com.vention.stockmarket.service;

import com.vention.stockmarket.domain.UserModel;
import com.vention.stockmarket.dto.request.UserRegisterDTO;
import com.vention.stockmarket.dto.response.ResponseDTO;

public interface UserService extends BaseService<UserModel> {
    ResponseDTO<Long> register(UserRegisterDTO registerDTO);
}
