package com.vention.stockmarket.feign.clients;


import com.vention.stockmarket.dto.response.CompanyInfoResponseDTO;
import com.vention.stockmarket.dto.response.GeneralStockResponseDTO;
import com.vention.stockmarket.dto.response.RealTimePriceResponseDTO;
import com.vention.stockmarket.dto.response.StockInfoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(url = "https://twelve-data1.p.rapidapi.com", name = "Stock")
public interface StockServiceFeign {

    @GetMapping("/stocks")
    GeneralStockResponseDTO<List<StockInfoResponseDTO>> getAllStocks(@RequestHeader("X-RapidAPI-Key") String key,
                                                                     @RequestHeader("X-RapidAPI-Host") String host,
                                                                     @RequestParam("exchange") String exchange);

    @GetMapping("/profile")
    CompanyInfoResponseDTO getLatestCompanyInfo(@RequestHeader("X-RapidAPI-Key") String key,
                                                @RequestHeader("X-RapidAPI-Host") String host,
                                                @RequestParam("symbol") String symbol);

    @GetMapping("/price")
    RealTimePriceResponseDTO getRealTimePrice(@RequestHeader("X-RapidAPI-Key") String key,
                                              @RequestHeader("X-RapidAPI-Host") String host,
                                              @RequestParam("symbol") String symbol);
}
