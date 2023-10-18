package com.vention.stockmarket.service.impl;

import com.vention.stockmarket.dto.response.FavouriteCompanyDTO;
import com.vention.stockmarket.repository.FavouriteCompaniesRepository;
import com.vention.stockmarket.repository.SecurityRepository;
import com.vention.stockmarket.repository.StockRepository;
import com.vention.stockmarket.repository.UserRepository;
import com.vention.stockmarket.service.FavouriteCompaniesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavouriteCompaniesServiceImpl implements FavouriteCompaniesService {

    private final FavouriteCompaniesRepository repository;
    private final SecurityRepository securityRepository;
    private final StockRepository stockRepository;
    @Override
    public void addToFavorites(String username, String companyName) {
        var byEmail = securityRepository.getByEmail(username);
        var bySymbol = stockRepository.findBySymbol(companyName);
        repository.add(byEmail.getUserId(), bySymbol.getSymbol());
    }

    @Override
    public List<FavouriteCompanyDTO> getAllByUsername(String username) {
        var byEmail = securityRepository.getByEmail(username);
        var companiesSymbols = repository.findByUserId(byEmail.getUserId());
        var all = stockRepository.findAll(companiesSymbols);
        var favouriteCompanies = all.stream().map(FavouriteCompanyDTO::new).toList();
        return favouriteCompanies;
    }

    @Override
    public void delete(String username, String symbol) {
        var byEmail = securityRepository.getByEmail(username);
        var bySymbol = stockRepository.findBySymbol(symbol);
        repository.delete(byEmail.getUserId(), bySymbol.getSymbol());
    }
}
