package com.vention.stockmarket.service.impl;

import com.vention.stockmarket.domain.UserModel;
import com.vention.stockmarket.dto.request.UserRegisterDTO;
import com.vention.stockmarket.dto.response.ResponseDTO;
import com.vention.stockmarket.exceptions.CustomResourceNotFoundException;
import com.vention.stockmarket.repository.UserRepository;
import com.vention.stockmarket.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseDTO<Long> create(UserModel userModel) {
        Optional<Long> userId = repository.create(userModel);
        return new ResponseDTO<>(true, userId.get());
    }

    @Override
    public ResponseDTO<Long> register(UserRegisterDTO registerDTO) {
        registerDTO.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        var userId = repository.registerUser(registerDTO);
        return new ResponseDTO<>(true, 200, "registered successfully", userId.get());
    }

    @Override
    public ResponseDTO<UserModel> getById(Long id) {
        Optional<UserModel> user = repository.getById(id);
        if (user.isEmpty()) {
            throw new CustomResourceNotFoundException("User not found with id : " + id);
        }
        return new ResponseDTO<>(true, user.get());
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
