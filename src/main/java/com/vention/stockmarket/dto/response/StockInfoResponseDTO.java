package com.vention.stockmarket.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StockInfoResponseDTO {
    private String symbol;
    private String name;
    private String currency;
    private String exchange;
    @JsonProperty(value = "mic_code")
    private String mixCode;
    private String country;
    private String type;
}
