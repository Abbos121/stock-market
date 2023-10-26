package com.vention.stockmarket.domain;

import com.vention.stockmarket.dto.request.UserUpdateDTO;
import com.vention.stockmarket.utils.DateUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

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
        this.dateOfBirth = DateUtils.convertStringToDate(updateDTO.getDateOfBirth());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserModel userModel = (UserModel) o;

        return id != null ? id.equals(userModel.id) : userModel.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
