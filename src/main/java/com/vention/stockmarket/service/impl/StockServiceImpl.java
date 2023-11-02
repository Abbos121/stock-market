package com.vention.stockmarket.service.impl;

import com.vention.stockmarket.domain.StockModel;
import com.vention.stockmarket.dto.response.CompanyInfoResponseDTO;
import com.vention.stockmarket.dto.response.StockInfoResponseDTO;
import com.vention.stockmarket.feign.clients.StockServiceFeign;
import com.vention.stockmarket.repository.StockRepository;
import com.vention.stockmarket.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockRepository repository;

    private final StockServiceFeign stockServiceFeign;

    @Override
    public List<StockModel> getStockList() {
        return repository.findAll();
    }

    @Override
    @Scheduled(cron = "0 29 17 * * ?")
    public void updateStockList() {
        var allStocks = stockServiceFeign
                .getAllStocks(RAPID_API_KEY, RAPID_API_HOST, "NASDAQ").getData();

        AtomicInteger numberOfStocksHavingPrice = new AtomicInteger(0);

        var stocks = allStocks.stream().map(dto -> {
            StockModel stock = new StockModel(dto.getSymbol(), dto.getName(), dto.getCurrency(),
                    dto.getExchange(), dto.getMixCode(), dto.getCountry(), dto.getType());
            setStockPrice(numberOfStocksHavingPrice, stock, dto);
            return stock;
        }).toList();

        repository.saveAllStocks(stocks);
    }

    @Override
    public CompanyInfoResponseDTO getLatestCompanyInfo(String symbol) {
        return stockServiceFeign.getLatestCompanyInfo(RAPID_API_KEY, RAPID_API_HOST, symbol);
    }

    private void setStockPrice(AtomicInteger numberOfStocksHavingPrice, StockModel stock, StockInfoResponseDTO dto) {
        // As I have limited number of requested per minute which is only 8, I am retrieving only 7 stock prices
        if (numberOfStocksHavingPrice.get() < 7) {
            var price = stockServiceFeign
                    .getRealTimePrice(RAPID_API_KEY, RAPID_API_HOST, dto.getSymbol());
            stock.setPrice(Double.parseDouble(price.getPrice()));
            numberOfStocksHavingPrice.getAndIncrement();
        }
    }
}
