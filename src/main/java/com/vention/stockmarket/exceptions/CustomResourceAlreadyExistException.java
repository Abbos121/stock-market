package com.vention.stockmarket.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CustomResourceAlreadyExistException extends RuntimeException {
    private String message;
}
