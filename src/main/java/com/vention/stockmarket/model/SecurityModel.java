package com.vention.stockmarket.model;

import lombok.Data;

@Data
public class SecurityModel {
    private Long id;
    private Long userId;
    private String email;
    private String password;
}
