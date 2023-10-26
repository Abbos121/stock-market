package com.vention.stockmarket.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserUpdateDTO {
    @NotNull
    private Long id;
    private String firstName;
    private String secondName;
    @NotNull
    @Pattern(regexp = "\\d{2}\\.\\d{2}\\.\\d{4}", message = "date format must be like dd.MM.yyyy")
    private String dateOfBirth;
}
