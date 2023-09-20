package com.vention.stockmarket.repository;

import com.vention.stockmarket.domain.SecurityModel;

import java.util.List;

public interface SecurityRepository extends BaseRepository<SecurityModel> {
    List<SecurityModel> getAll();
}
