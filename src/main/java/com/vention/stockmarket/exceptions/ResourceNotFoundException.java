package com.vention.stockmarket.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ResourceNotFoundException extends RuntimeException {
    String message;
}
