package com.vention.stockmarket.service.impl;

import com.vention.stockmarket.domain.SecurityCredentials;
import com.vention.stockmarket.domain.UserModel;
import com.vention.stockmarket.dto.request.UserRegisterDTO;
import com.vention.stockmarket.dto.response.ResponseDTO;
import com.vention.stockmarket.exceptions.CustomResourceAlreadyExistException;
import com.vention.stockmarket.exceptions.CustomResourceNotFoundException;
import com.vention.stockmarket.exceptions.CustomUnauthorizedException;
import com.vention.stockmarket.repository.SecurityRepository;
import com.vention.stockmarket.repository.UserRepository;
import com.vention.stockmarket.service.SecurityHelperService;
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
    private final SecurityHelperService securityHelperService;
    private final SecurityRepository securityRepository;

    @Override
    public ResponseDTO<Long> create(UserModel userModel) {
        Optional<Long> userId = repository.create(userModel);
        return new ResponseDTO<>(true, userId.get());
    }

    @Override
    public ResponseDTO<Long> register(UserRegisterDTO registerDTO) {
        if (securityRepository.getByEmail(registerDTO.getEmail()).isPresent()) {
            throw new CustomResourceAlreadyExistException("User already registered with this email " + registerDTO.getEmail());
        }
        registerDTO.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        var userId = repository.registerUser(registerDTO);
        return new ResponseDTO<>(true, 200, "registered successfully", userId.get());
    }

    @Override
    public ResponseDTO<UserModel> getByUsername(String username) {
        if (!SecurityHelperService.hasUserPermissions(username)) {
            throw new CustomUnauthorizedException();
        }
        Optional<SecurityCredentials> securityCredentials = securityRepository.getByEmail(username);
        if (securityCredentials.isEmpty()) {
            throw new CustomResourceNotFoundException("User not found with username : " + username);
        }
        var userModel = repository.getById(securityCredentials.get().getUserId());
        return new ResponseDTO<>(true, userModel.get());
    }

    @Override
    public ResponseDTO<UserModel> getById(Long id) {
        if (!securityHelperService.hasUserPermissions(id)) {
            throw new CustomUnauthorizedException();
        }
        Optional<UserModel> user = repository.getById(id);
        if (user.isEmpty()) {
            throw new CustomResourceNotFoundException("User not found with id : " + id);
        }
        return new ResponseDTO<>(true, user.get());
    }

    @Override
    public void update(UserModel userModel) {
        if (!securityHelperService.hasUserPermissions(userModel.getId())) {
            throw new CustomUnauthorizedException();
        }
        repository.update(userModel);
    }

    @Override
    public void delete(Long id) {
        if (!securityHelperService.hasUserPermissions(id)) {
            throw new CustomUnauthorizedException();
        }
        repository.delete(id);
    }

    @Override
    public ResponseDTO<List<UserModel>> getAll() {
        if (!SecurityHelperService.isAdmin()) {
            throw new CustomUnauthorizedException();
        }
        List<UserModel> users = repository.getAll();
        return new ResponseDTO<>(true, users);
    }
}
