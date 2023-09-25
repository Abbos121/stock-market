package com.vention.stockmarket.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterDTO {
    @Email(message = "This is not a valid email format")
    @NotNull
    private String email;

    @NotNull
    @Size(min = 6, message = "password must be at least 6 digits")
    private String password;

    @NotNull
    private String firstName;

    @NotNull
    private String secondName;

    @NotNull
    @Pattern(regexp = "\\d{2}\\.\\d{2}\\.\\d{4}", message = "date format must be like dd.MM.yyyy")
    private String dateOfBirth;
}
