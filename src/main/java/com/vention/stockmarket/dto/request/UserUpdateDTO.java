package com.vention.stockmarket.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.util.Date;

@Data
@Schema(title = "User update Details")
public class UserUpdateDTO {
    @NotNull
    private Long id;
    @NotNull
    private String firstName;
    @NotNull
    private String secondName;
    @NotNull
    @Past(message = "Date of birth must be in the past")
    private Date dateOfBirth;
}
