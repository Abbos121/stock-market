package com.vention.stockmarket.controller;

import com.vention.stockmarket.domain.SecurityCredentials;
import com.vention.stockmarket.dto.request.PasswordUpdateDTO;
import com.vention.stockmarket.dto.request.RolesUpdateDTO;
import com.vention.stockmarket.dto.response.ResponseDTO;
import com.vention.stockmarket.service.SecurityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService service;

    @PostMapping
    public ResponseEntity<?> create(SecurityCredentials securityCredentials) {
        return ResponseEntity.ok(service.create(securityCredentials));
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getById(@PathVariable("email") String email) {
        return ResponseEntity.ok(service.getByEmail(email));
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> update(@RequestBody @Valid PasswordUpdateDTO updateDTO) {
        service.update(new SecurityCredentials(updateDTO));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/edit-role")
    public ResponseEntity<?> update(@RequestBody @Valid RolesUpdateDTO updateDTO) {
        ResponseDTO<?> responseDTO = service.editRoles(updateDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}