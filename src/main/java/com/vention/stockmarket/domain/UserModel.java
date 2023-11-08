package com.vention.stockmarket.domain;

import com.vention.stockmarket.dto.request.UserRegisterDTO;
import com.vention.stockmarket.dto.request.UserUpdateDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

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

    public UserModel(UserUpdateDTO updateDTO) {
        this.id = updateDTO.getId();
        this.firstName = updateDTO.getFirstName();
        this.secondName = updateDTO.getSecondName();
        this.dateOfBirth = updateDTO.getDateOfBirth();
    }

    public UserModel(UserRegisterDTO userRegisterDTO) {
        this.firstName = userRegisterDTO.getFirstName();
        this.secondName = userRegisterDTO.getSecondName();
        this.dateOfBirth = userRegisterDTO.getDateOfBirth();
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserModel userModel = (UserModel) o;

        return Objects.equals(id, userModel.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
