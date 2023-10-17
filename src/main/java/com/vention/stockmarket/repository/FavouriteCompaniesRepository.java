package com.vention.stockmarket.repository;

import java.util.List;

public interface FavouriteCompaniesRepository extends DatabaseCredentials {
    void add(Long userId, String companySymbol);
    void delete(Long userId, String companySymbol);

    List<Long> findByUserId(Long userId);
    boolean existsByUserIdAndCompanySymbol(Long userId, String companySymbol);
}
