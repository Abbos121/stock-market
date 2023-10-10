package com.vention.stockmarket.repository;

public interface BaseRepository<T> extends DatabaseCredentials {
    Long create(T t);
    T getById(Long id);
    void update(T t);
    void delete(Long id);
}
