package com.vention.stockmarket.dto.filter;

import lombok.Data;

@Data
public class StockListFilterDTO {
    private String symbolSearch = "";
    private int page = 0;
    private int size = 15;
}