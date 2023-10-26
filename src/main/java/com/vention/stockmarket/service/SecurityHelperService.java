package com.vention.stockmarket.service;

import org.springframework.security.core.context.SecurityContextHolder;

public interface SecurityHelperService {

    static boolean hasUserPermissions(String username) {
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        return (authenticatedUsername.equals(username) || isAdmin());
    }

    static boolean isAdmin() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
    }

    boolean hasUserPermissions(Long userId);
}
