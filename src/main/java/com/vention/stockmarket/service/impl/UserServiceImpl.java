package com.vention.stockmarket.service.impl;

import com.vention.stockmarket.domain.UserModel;
import com.vention.stockmarket.dto.request.UserRegisterDTO;
import com.vention.stockmarket.dto.response.ResponseDTO;
import com.vention.stockmarket.exceptions.ResourceNotFoundException;
import com.vention.stockmarket.repository.SecurityRepository;
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
    public ResponseDTO<Long> register(UserRegisterDTO registerDTO) {
//        UserModel user = new UserModel(registerDTO.getFirstName(), registerDTO.getSecondName(),
//                DateUtils.convertStringToDate(registerDTO.getDateOfBirth()), LocalDateTime.now());
//        Long userId = repository.create(user);
//        SecurityModel security = new SecurityModel(userId, registerDTO.getEmail(), registerDTO.getPassword());
//        securityRepository.create(security);

        var userId = repository.registerUser(registerDTO);
        return new ResponseDTO<>(true, 200, "registered successfully", userId);
    }

    @Override
    public ResponseDTO<UserModel> getById(Long id) {
        UserModel user = repository.getById(id);
        if (user == null) {
            throw new ResourceNotFoundException("User not found with id : " + id);
        }
        return new ResponseDTO<>(true, user);
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
