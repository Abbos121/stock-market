package com.vention.stockmarket.service.impl;

import com.vention.stockmarket.dto.response.FavouriteCompanyDTO;
import com.vention.stockmarket.exceptions.CustomUnauthorizedException;
import com.vention.stockmarket.repository.FavouriteCompaniesRepository;
import com.vention.stockmarket.repository.SecurityRepository;
import com.vention.stockmarket.repository.StockRepository;
import com.vention.stockmarket.service.FavouriteCompaniesService;
import com.vention.stockmarket.service.SecurityHelperService;
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
        if (!SecurityHelperService.hasUserPermissions(username)) {
            throw new CustomUnauthorizedException();
        }
        var byEmail = securityRepository.getByEmail(username);
        var bySymbol = stockRepository.findBySymbol(companyName).get();
        repository.add(byEmail.get().getUserId(), bySymbol.getSymbol());
    }

    @Override
    public List<FavouriteCompanyDTO> getAllByUsername(String username) {
        if (!SecurityHelperService.hasUserPermissions(username)) {
            throw new CustomUnauthorizedException();
        }
        var byEmail = securityRepository.getByEmail(username);
        var companiesSymbols = repository.findByUserId(byEmail.get().getUserId());
        var all = stockRepository.findAll(companiesSymbols);
        var favouriteCompanies = all.stream().map(FavouriteCompanyDTO::new).toList();
        return favouriteCompanies;
    }

    @Override
    public void delete(String username, String symbol) {
        if (!SecurityHelperService.hasUserPermissions(username)) {
            throw new CustomUnauthorizedException();
        }
        var byEmail = securityRepository.getByEmail(username);
        var bySymbol = stockRepository.findBySymbol(symbol).get();
        repository.delete(byEmail.get().getUserId(), bySymbol.getSymbol());
    }
}
