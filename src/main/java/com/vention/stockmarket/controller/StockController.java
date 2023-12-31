package com.vention.stockmarket.controller;

import com.vention.stockmarket.domain.StockModel;
import com.vention.stockmarket.dto.filter.StockListFilterDTO;
import com.vention.stockmarket.dto.response.CompanyInfoResponseDTO;
import com.vention.stockmarket.service.StockService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "4. Stocks", description = "endpoints for getting stock information")
public class StockController {

    private final StockService service;

    @GetMapping("/all")
    public ResponseEntity<List<StockModel>> getAll(StockListFilterDTO filterDTO) {
        var stockList = service.getStockList(filterDTO);
        return ResponseEntity.ok(stockList);
    }

    @GetMapping
    public ResponseEntity<CompanyInfoResponseDTO> getCompanyInfoBySymbol(@RequestParam(value = "symbol") String symbol) {
        var companyInfo = service.getLatestCompanyInfo(symbol);
        return ResponseEntity.ok(companyInfo);
    }
}