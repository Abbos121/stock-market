package com.vention.stockmarket.service;

import com.vention.stockmarket.domain.SecurityCredentials;
import com.vention.stockmarket.dto.request.AuthRequestDTO;
import com.vention.stockmarket.dto.request.RolesUpdateDTO;
import com.vention.stockmarket.dto.response.ResponseDTO;

public interface SecurityService extends BaseService<SecurityCredentials> {
    String generateToken(AuthRequestDTO authRequest);

    ResponseDTO<SecurityCredentials> getByEmail(String email);

    void editRoles(RolesUpdateDTO updateDTO);
}
