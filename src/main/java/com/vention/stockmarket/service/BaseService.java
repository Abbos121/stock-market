package com.vention.stockmarket.service;

import com.vention.stockmarket.dto.ResponseDTO;

import java.util.List;

public interface BaseService <T> {
    ResponseDTO<Long> create(T t);

    ResponseDTO<T> getById(Long id);

    void update(T t);

    void delete(Long id);

    ResponseDTO<List<T>> getAll();
}
