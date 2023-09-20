package com.vention.stockmarket.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRegisterDTO {
    @Email
    @NotNull
    private String email;

    @NotNull
    @Min(value = 6, message = "password must be at least 6 chars")
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String secondName;

    @NotNull
    @Pattern(regexp = "dd.MM.yyyy", message = "date format must be like dd.MM.yyyy")
    private String dateOfBirth;
}
