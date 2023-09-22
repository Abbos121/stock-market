package com.vention.stockmarket.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UserModel {
    private Long id;
    private String firstName;
    private String secondName;
    private Date dateOfBirth;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserModel(String firstName, String secondName, Date dateOfBirth, LocalDateTime createdAt) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
    }
}
