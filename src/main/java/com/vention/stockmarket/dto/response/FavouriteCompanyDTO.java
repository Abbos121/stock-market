package com.vention.stockmarket.dto.response;

import com.vention.stockmarket.domain.StockModel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FavouriteCompanyDTO {
    private Long id;
    private String symbol;
    private String name;
    private String currency;
    private String exchange;
    private String country;

    public FavouriteCompanyDTO(StockModel model) {
        this.id = model.getId();
        this.symbol = model.getSymbol();
        this.name = model.getName();
        this.currency = model.getCurrency();
        this.exchange = model.getExchange();
    }
}
