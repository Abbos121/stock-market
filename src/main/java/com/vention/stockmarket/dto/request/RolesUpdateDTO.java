package com.vention.stockmarket.dto.request;

import com.vention.stockmarket.enumuration.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class RolesUpdateDTO {
    @Email(message = "This is not a valid email format")
    @NotNull
    private String email;

    @NotNull
    @Size(min = 1)
    private Set<Role> roles;
}
