package com.vention.stockmarket.auth;

import com.vention.stockmarket.enumuration.Role;
import com.vention.stockmarket.repository.SecurityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final SecurityRepository securityRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var securityCredentials = securityRepository.getByEmail(username);
        if (securityCredentials.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return User
                .withUsername(securityCredentials.get().getEmail())
                .password(securityCredentials.get().getPassword())
                .roles(Role.convertSetToArray(securityCredentials.get().getRoles()))
                .build();
    }
}
