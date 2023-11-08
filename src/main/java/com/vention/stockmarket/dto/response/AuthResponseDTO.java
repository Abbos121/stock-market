package com.vention.stockmarket.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(title = "successful login response")
public class AuthResponseDTO {
    private String token;
}