package com.vention.stockmarket.service.impl;

import com.vention.stockmarket.dto.ResponseDTO;
import com.vention.stockmarket.domain.SecurityModel;
import com.vention.stockmarket.repository.SecurityRepository;
import com.vention.stockmarket.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService {

    private final SecurityRepository repository;

    @Override
    public ResponseDTO<Long> create(SecurityModel securityModel) {
        var userId = repository.create(securityModel);
        return new ResponseDTO<>(true, userId);
    }

    @Override
    public ResponseDTO<SecurityModel> getById(Long id) {
        var securityModel = repository.getById(id);
        if (securityModel != null) {
            return new ResponseDTO<>(true, securityModel);
        } else {
            return new ResponseDTO<>(false, 404, "User not found", null);
        }
    }

    @Override
    public void update(SecurityModel securityModel) {
        repository.update(securityModel);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public ResponseDTO<List<SecurityModel>> getAll() {
        var all = repository.getAll();
        return new ResponseDTO<>(true, all);
    }
}
