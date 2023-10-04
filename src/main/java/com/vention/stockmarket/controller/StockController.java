package com.vention.stockmarket.controller;

import com.vention.stockmarket.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService service;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        var stockList = service.getStockList();
        return ResponseEntity.ok(stockList);
    }

    @GetMapping
    public ResponseEntity<?> getCompanyInfoBySymbol(@RequestParam(value = "symbol") String symbol) {
        var companyInfo = service.getLatestCompanyInfo(symbol);
        return ResponseEntity.ok(companyInfo);
    }
}
