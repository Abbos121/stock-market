package com.vention.stockmarket.service;

import com.vention.stockmarket.dto.response.FavouriteCompanyDTO;

import java.util.List;

public interface FavouriteCompaniesService {

    void addToFavorites(String username, String companySymbol);

    List<FavouriteCompanyDTO> getAllByUsername(String username);

    void delete(String username, String companyName);
}
