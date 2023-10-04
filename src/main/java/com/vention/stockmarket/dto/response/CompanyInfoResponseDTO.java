package com.vention.stockmarket.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CompanyInfoResponseDTO {
    private String symbol;
    private String name;
    private String exchange;
    @JsonProperty("mic_code")
    private String mixCode;
    private String sector;
    private String industry;
    private int employees;
    private String website;
    private String description;
    private String type;
    @JsonProperty("CEO")
    private String ceo;
    private String address;
    private String city;
    private String zip;
    private String state;
    private String country;
    private String phone;
}
