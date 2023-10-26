package com.vention.stockmarket.service.impl;

import com.vention.stockmarket.repository.SecurityRepository;
import com.vention.stockmarket.service.SecurityHelperService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityHelperServiceImpl implements SecurityHelperService {

    private final SecurityRepository securityRepository;

    @Override
    public boolean hasUserPermissions(Long userId) {
        String username = securityRepository.getByUserId(userId).get().getEmail();
        String authenticatedUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        boolean isAdmin = SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));

        return (authenticatedUsername.equals(username) || isAdmin);
    }
}
