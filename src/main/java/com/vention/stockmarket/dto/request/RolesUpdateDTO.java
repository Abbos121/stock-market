package com.vention.stockmarket.dto.request;

import com.vention.stockmarket.enumuration.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
@Schema(title = "Roles Update")
public class RolesUpdateDTO {
    @Email(message = "This is not a valid email format")
    @NotNull
    private String email;

    @NotNull
    @Size(min = 1)
    @Schema(description = "at least there must be 1 role when updating")
    private Set<Role> roles;
}
