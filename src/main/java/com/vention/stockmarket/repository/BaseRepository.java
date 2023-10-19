package com.vention.stockmarket.repository;

import com.vention.stockmarket.domain.SecurityModel;

import java.util.Optional;

public interface BaseRepository<T> {
    Optional<Long> create(T t);
    Optional<T> getById(Long id);
    void update(T t);
    void delete(Long id);
}
