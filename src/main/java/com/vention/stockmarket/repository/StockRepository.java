package com.vention.stockmarket.repository;

import com.vention.stockmarket.domain.StockModel;

import java.util.List;

public interface StockRepository {

    void saveAllStocks(List<StockModel> stocks);

    List<StockModel> findAll();
    List<StockModel> findAll(List<Long> idList);

    StockModel findBySymbol(String symbol);
}
