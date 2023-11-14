package com.vention.stockmarket.exceptions;

import lombok.Getter;

@Getter
public class CustomFeignClientException extends RuntimeException {
    private int status;
    private String reason;
    private String body;

    public CustomFeignClientException(int status, String reason, String body) {
        this.status = status;
        this.reason = reason;
        this.body = body;
    }
}