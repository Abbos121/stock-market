package com.vention.stockmarket.domain;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
public class UserModel {
    private Long id;
    private String firstName;
    private String secondName;
    private Date dateOfBirth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
