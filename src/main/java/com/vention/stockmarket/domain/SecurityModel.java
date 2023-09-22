package com.vention.stockmarket.domain;

import com.vention.stockmarket.enumuration.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class SecurityModel {
    private Long id;
    private Long userId;
    private String email;
    private String password;
    private Set<Role> roles;

    public SecurityModel(Long userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        roles = Set.of(Role.USER);
    }
}
