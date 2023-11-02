package com.vention.stockmarket.service.impl;

import com.vention.stockmarket.dto.response.FavouriteCompanyDTO;
import com.vention.stockmarket.exceptions.CustomResourceNotFoundException;
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
        var securityCredentials = securityRepository.getByEmail(username)
                .orElseThrow(() -> new CustomResourceNotFoundException("security credentials not found with email " + username));
        var stockModel = stockRepository.findBySymbol(companyName)
                .orElseThrow(() -> new CustomResourceNotFoundException("stock not found with company symbol " + companyName));
        repository.add(securityCredentials.getUserId(), stockModel.getSymbol());
    }

    @Override
    public List<FavouriteCompanyDTO> getAllByUsername(String username) {
        if (!SecurityHelperService.hasUserPermissions(username)) {
            throw new CustomUnauthorizedException();
        }
        var securityCredentials = securityRepository.getByEmail(username)
                .orElseThrow(() -> new CustomResourceNotFoundException("security credentials not found with email " + username));
        var companiesSymbols = repository.findByUserId(securityCredentials.getUserId());
        var all = stockRepository.findAll(companiesSymbols);
        var favouriteCompanies = all.stream().map(FavouriteCompanyDTO::new).toList();
        return favouriteCompanies;
    }

    @Override
    public void delete(String username, String symbol) {
        if (!SecurityHelperService.hasUserPermissions(username)) {
            throw new CustomUnauthorizedException();
        }
        var securityCredentials = securityRepository.getByEmail(username)
                .orElseThrow(() -> new CustomResourceNotFoundException("security credentials not found with email " + username));
        var stock = stockRepository.findBySymbol(symbol)
                .orElseThrow(() -> new CustomResourceNotFoundException("stock not found with company symbol " + symbol));
        repository.delete(securityCredentials.getUserId(), stock.getSymbol());
    }
}
