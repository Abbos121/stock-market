package com.vention.stockmarket.service;


import com.vention.stockmarket.domain.StockModel;
import com.vention.stockmarket.dto.filter.StockListFilterDTO;
import com.vention.stockmarket.dto.response.CompanyInfoResponseDTO;

import java.util.List;

public interface StockService {
    String RAPID_API_KEY = "39bb881063msh2f7ba7ff467c64ep1caba2jsn6700be56c694";
    String RAPID_API_HOST = "twelve-data1.p.rapidapi.com";

    List<StockModel> getStockList(StockListFilterDTO filterDTO);

    void updateStockList();

    CompanyInfoResponseDTO getLatestCompanyInfo(String symbol);
}
