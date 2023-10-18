package com.vention.stockmarket.repository;

import com.vention.stockmarket.domain.SecurityModel;

import java.util.List;
import java.util.Optional;

public interface SecurityRepository extends BaseRepository<SecurityModel> {
    List<SecurityModel> getAll();

    Optional<SecurityModel> getByEmail(String email);
}
