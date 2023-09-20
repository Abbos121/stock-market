package com.vention.stockmarket.domain;

import com.vention.stockmarket.enumuration.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SecurityModel {
    private Long id;
    private Long userId;
    private String email;
    private String password;
    private Set<Role> roles;
}
