package com.vention.stockmarket.repository;

import com.vention.stockmarket.domain.SecurityCredentials;

import java.util.List;
import java.util.Optional;

public interface SecurityRepository extends BaseRepository<SecurityCredentials> {
    List<SecurityCredentials> getAll();

    Optional<SecurityCredentials> getByEmail(String email);
}
