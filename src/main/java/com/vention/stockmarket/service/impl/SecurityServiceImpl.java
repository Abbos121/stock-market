package com.vention.stockmarket.service.impl;

import com.vention.stockmarket.domain.SecurityCredentials;
import com.vention.stockmarket.dto.request.AuthRequestDTO;
import com.vention.stockmarket.dto.request.RolesUpdateDTO;
import com.vention.stockmarket.dto.response.ResponseDTO;
import com.vention.stockmarket.exceptions.CustomResourceNotFoundException;
import com.vention.stockmarket.exceptions.CustomUnauthorizedException;
import com.vention.stockmarket.repository.SecurityRepository;
import com.vention.stockmarket.service.SecurityHelperService;
import com.vention.stockmarket.service.SecurityService;
import com.vention.stockmarket.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final SecurityRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public ResponseDTO<Long> create(SecurityCredentials securityCredentials) {
        var userId = repository.create(securityCredentials);
        return new ResponseDTO<>(true, userId.get());
    }

    @Override
    public ResponseDTO<SecurityCredentials> getById(Long id) {
        var securityModel = repository.getById(id);
        if (securityModel.isEmpty()) {
            throw new CustomResourceNotFoundException("Security model not found with id : " + id);
        }
        if (!SecurityHelperService.hasUserPermissions(securityModel.get().getEmail())) {
            throw new CustomUnauthorizedException();
        }
        return new ResponseDTO<>(true, securityModel.get());
    }

    @Override
    public void update(SecurityCredentials securityCredentials) {
        if (!SecurityHelperService.hasUserPermissions(securityCredentials.getEmail())) {
            throw new CustomUnauthorizedException();
        }
        securityCredentials.setPassword(passwordEncoder.encode(securityCredentials.getPassword()));
        repository.update(securityCredentials);
    }

    @Override
    public void delete(Long id) {
        var securityModel = repository.getById(id);
        if (securityModel.isEmpty()) {
            throw new CustomResourceNotFoundException("Security model not found with id : " + id);
        }
        if (!SecurityHelperService.hasUserPermissions(securityModel.get().getEmail())) {
            throw new CustomUnauthorizedException();
        }
        repository.delete(id);
    }

    @Override
    public ResponseDTO<List<SecurityCredentials>> getAll() {
        if (!SecurityHelperService.isAdmin()) {
            throw new CustomUnauthorizedException();
        }
        var all = repository.getAll();
        return new ResponseDTO<>(true, all);
    }

    @Override
    public String generateToken(AuthRequestDTO authRequest) {
        var securityCredentials = repository.getByEmail(authRequest.getEmail());
        if (securityCredentials.isPresent() && passwordEncoder
                .matches(authRequest.getPassword(), securityCredentials.get().getPassword())) {
            return jwtUtils.generateJwt(authRequest.getEmail());
        }
        return "";
    }

    @Override
    public ResponseDTO<SecurityCredentials> getByEmail(String email) {
        if (!SecurityHelperService.hasUserPermissions(email)) {
            throw new CustomUnauthorizedException();
        }
        var securityCredentials = repository.getByEmail(email);
        if (securityCredentials.isEmpty()) {
            throw new CustomResourceNotFoundException("security credentials not found with this email " + email);
        }
        return new ResponseDTO<>(true, securityCredentials.get());
    }

    @Override
    public void editRoles(RolesUpdateDTO updateDTO) {
        if (!SecurityHelperService.isAdmin()) {
            throw new CustomUnauthorizedException();
        }
        repository.updateRoles(updateDTO);
    }
}
