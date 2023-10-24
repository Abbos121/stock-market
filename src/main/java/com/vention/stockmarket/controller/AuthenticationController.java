package com.vention.stockmarket.controller;


import com.vention.stockmarket.dto.request.AuthRequestDTO;
import com.vention.stockmarket.dto.request.UserRegisterDTO;
import com.vention.stockmarket.dto.response.AuthResponseDTO;
import com.vention.stockmarket.dto.response.ResponseDTO;
import com.vention.stockmarket.service.SecurityService;
import com.vention.stockmarket.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final SecurityService securityService;
    private final UserService userService;

    @PostMapping("/registration")
    public ResponseEntity<ResponseDTO<Long>> registerUser(@RequestBody @Valid UserRegisterDTO registerDTO) {
        return ResponseEntity.ok(userService.register(registerDTO));
    }

    @PostMapping
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody @Valid AuthRequestDTO authRequest) {
        String token = securityService.generateToken(authRequest);
        if (token.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new AuthResponseDTO(token));
    }
}