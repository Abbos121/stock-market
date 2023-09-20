package com.vention.stockmarket.dto;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ResponseDTO <T> {
    private boolean success;
    private Integer code;
    private String message;
    private T data;

    public ResponseDTO(boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseDTO(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public ResponseDTO(boolean success, Integer code) {
        this.success = success;
        this.code = code;
    }
}
