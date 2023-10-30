package com.vention.stockmarket.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class StockModel {
    private Long id;
    private String symbol;
    private String name;
    private String currency;
    private String exchange;
    private String mixCode;
    private String country;
    private String type;
    private double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public StockModel(String symbol, String name, String currency, String exchange, String mixCode, String country, String type) {
        this.symbol = symbol;
        this.name = name;
        this.currency = currency;
        this.exchange = exchange;
        this.mixCode = mixCode;
        this.country = country;
        this.type = type;
    }
}
