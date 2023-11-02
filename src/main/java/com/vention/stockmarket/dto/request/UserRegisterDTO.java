package com.vention.stockmarket.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

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
    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;
}
