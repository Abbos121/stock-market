package com.vention.stockmarket.repository;

import java.util.List;

public interface FavouriteCompaniesRepository {
    void add(Long userId, String companySymbol);
    void delete(Long userId, String companySymbol);

    List<String> findByUserId(Long userId);
    boolean existsByUserIdAndCompanySymbol(Long userId, String companySymbol);
}
