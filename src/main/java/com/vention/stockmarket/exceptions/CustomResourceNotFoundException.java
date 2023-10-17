package com.vention.stockmarket.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomResourceNotFoundException extends RuntimeException {
    String message;
}
