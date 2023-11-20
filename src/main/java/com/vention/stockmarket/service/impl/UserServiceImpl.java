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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityHelperService securityHelperService;
    private final SecurityRepository securityRepository;

    @Override
    public ResponseDTO<Long> create(UserModel userModel) {
        Long userId = repository.create(userModel)
                .orElseThrow(CustomResourceNotFoundException::new);
        return new ResponseDTO<>(true, userId);
    }

    @Override
    public ResponseDTO<Long> register(UserRegisterDTO registerDTO) {
        if (securityRepository.getByEmail(registerDTO.getEmail()).isPresent()) {
            throw new CustomResourceAlreadyExistException("User already registered with this email " + registerDTO.getEmail());
        }
        registerDTO.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        var userId = repository.registerUser(registerDTO)
                .orElseThrow(CustomResourceNotFoundException::new);
        return new ResponseDTO<>(true, 200, "registered successfully", userId);
    }

    @Override
    public ResponseDTO<UserModel> getByUsername(String username) {
        if (!SecurityHelperService.hasUserPermissions(username)) {
            throw new CustomUnauthorizedException();
        }
        SecurityCredentials securityCredentials = securityRepository.getByEmail(username)
                .orElseThrow(() -> new CustomResourceNotFoundException("User not found with username : " + username));
        var user = repository.getById(securityCredentials.getUserId())
                .orElseThrow(() -> new CustomResourceNotFoundException("User not found with id " + securityCredentials.getUserId()));

        return new ResponseDTO<>(true, user);
    }

    @Override
    public ResponseDTO<UserModel> getById(Long id) {
        if (!securityHelperService.hasUserPermissions(id)) {
            throw new CustomUnauthorizedException();
        }
        UserModel user = repository.getById(id)
                .orElseThrow(() -> new CustomResourceNotFoundException("User not found with id : " + id));

        return new ResponseDTO<>(true, user);
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
        List<UserModel> users = repository.getAll();
        return new ResponseDTO<>(true, users);
    }
}
