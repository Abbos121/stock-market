package com.vention.stockmarket.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralStockResponseDTO <T> {
    T data;
    String status;
}
