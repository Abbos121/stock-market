package com.vention.stockmarket.repository;

import com.vention.stockmarket.domain.StockModel;

import java.util.List;

public interface StockRepository {

    void saveAllStocks(List<StockModel> stocks);

    List<StockModel> findAll();
    List<StockModel> findAll(List<String> companySymbols);

    StockModel findBySymbol(String symbol);
}
