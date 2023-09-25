package com.vention.stockmarket.service.impl;

import com.vention.stockmarket.dto.response.ResponseDTO;
import com.vention.stockmarket.domain.SecurityModel;
import com.vention.stockmarket.exceptions.ResourceNotFoundException;
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
        if (securityModel == null)
            throw new ResourceNotFoundException("Security model not found with id : " + id);

        return new ResponseDTO<>(true, securityModel);
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
