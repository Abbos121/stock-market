package com.vention.stockmarket.service.impl;

import com.vention.stockmarket.domain.StockModel;
import com.vention.stockmarket.dto.response.CompanyInfoResponseDTO;
import com.vention.stockmarket.feign.clients.StockServiceFeign;
import com.vention.stockmarket.repository.StockRepository;
import com.vention.stockmarket.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository repository;

    private final StockServiceFeign stockServiceFeign;

    @Override
    public List<StockModel> getStockList() {
        List<StockModel> allStocks = repository.findAll();
        return allStocks;
    }

    @Override
    @Scheduled(cron = "0 0 5 * * ?")
    public void updateStockList() {
        var allStocks = stockServiceFeign
                .getAllStocks(RAPID_API_KEY, RAPID_API_HOST, "NASDAQ").getData();

        var stocks = allStocks.stream().map(dto -> {
            StockModel stock = new StockModel(dto.getSymbol(), dto.getName(), dto.getCurrency(),
                    dto.getExchange(), dto.getMixCode(), dto.getCountry(), dto.getType());
            return stock;
        }).toList();

        repository.saveAllStocks(stocks);
    }

    @Override
    public CompanyInfoResponseDTO getLatestCompanyInfo(String symbol) {
        var companyInfo = stockServiceFeign.getLatestCompanyInfo(RAPID_API_KEY, RAPID_API_HOST, symbol);
        return companyInfo;
    }
}
