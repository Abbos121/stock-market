package com.vention.stockmarket.repository;

import java.util.List;

public interface FavouriteCompaniesRepository extends DatabaseCredentials {
    void add(Long userId, Long companyId);
    void delete(Long userId, Long companyId);

    List<Long> findByUserId(Long userId);
    boolean existsByUserIdAndCompanyId(Long userId, Long companyId);
}
