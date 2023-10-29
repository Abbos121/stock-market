package com.vention.stockmarket.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vention.stockmarket.dto.request.PasswordUpdateDTO;
import com.vention.stockmarket.enumuration.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class SecurityCredentials {
    private Long id;
    private Long userId;
    private String email;
    @JsonIgnore
    private String password;
    private Set<Role> roles;

    public SecurityCredentials(Long userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        roles = Set.of(Role.USER);
    }

    public SecurityCredentials(PasswordUpdateDTO updateDTO) {
        this.email = updateDTO.getEmail();
        this.password = updateDTO.getPassword();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SecurityCredentials that = (SecurityCredentials) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
