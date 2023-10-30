package com.vention.stockmarket.repository;

import com.vention.stockmarket.domain.SecurityCredentials;
import com.vention.stockmarket.dto.request.RolesUpdateDTO;

import java.util.List;
import java.util.Optional;

public interface SecurityRepository extends BaseRepository<SecurityCredentials> {
    List<SecurityCredentials> getAll();

    Optional<SecurityCredentials> getByEmail(String email);

    Optional<SecurityCredentials> getByUserId(Long userId);

    void updateRoles(RolesUpdateDTO updateDTO);
}
