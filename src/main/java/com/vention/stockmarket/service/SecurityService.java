package com.vention.stockmarket.service;

import com.vention.stockmarket.domain.SecurityCredentials;
import com.vention.stockmarket.dto.request.AuthRequestDTO;

public interface SecurityService extends BaseService<SecurityCredentials> {
    String generateToken(AuthRequestDTO authRequest);
}
