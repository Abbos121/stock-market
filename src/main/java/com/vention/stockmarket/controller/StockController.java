package com.vention.stockmarket.controller;

import com.vention.stockmarket.domain.StockModel;
import com.vention.stockmarket.dto.response.CompanyInfoResponseDTO;
import com.vention.stockmarket.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService service;

    @GetMapping("/all")
    public ResponseEntity<List<StockModel>> getAll() {
        var stockList = service.getStockList();
        return ResponseEntity.ok(stockList);
    }

    @GetMapping
    public ResponseEntity<CompanyInfoResponseDTO> getCompanyInfoBySymbol(@RequestParam(value = "symbol") String symbol) {
        var companyInfo = service.getLatestCompanyInfo(symbol);
        return ResponseEntity.ok(companyInfo);
    }
}