package com.vention.stockmarket.service.impl;

import com.vention.stockmarket.dto.ResponseDTO;
import com.vention.stockmarket.domain.UserModel;
import com.vention.stockmarket.repository.UserRepository;
import com.vention.stockmarket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public ResponseDTO<Long> create(UserModel userModel) {
        Long userId = repository.create(userModel);
        return new ResponseDTO<>(true, userId);
    }

    @Override
    public ResponseDTO<UserModel> getById(Long id) {
        UserModel user = repository.getById(id);
        if (user != null) {
            return new ResponseDTO<>(true, user);
        } else {
            return new ResponseDTO<>(false, 404, "User not found", null);
        }
    }

    @Override
    public void update(UserModel userModel) {
        repository.update(userModel);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public ResponseDTO<List<UserModel>> getAll() {
        List<UserModel> users = repository.getAll();
        return new ResponseDTO<>(true, users);
    }
}
