package com.vention.stockmarket.model;

import lombok.Data;

import java.sql.Date;
import java.time.LocalDateTime;

@Data
public class UserModel {
    private Long id;
    private String firstName;
    private String secondName;
    private Date dateOfBirth;
//    private Set<Role> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
