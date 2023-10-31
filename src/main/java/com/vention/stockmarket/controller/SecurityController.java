package com.vention.stockmarket.controller;

import com.vention.stockmarket.domain.SecurityCredentials;
import com.vention.stockmarket.dto.request.PasswordUpdateDTO;
import com.vention.stockmarket.dto.request.RolesUpdateDTO;
import com.vention.stockmarket.dto.response.ResponseDTO;
import com.vention.stockmarket.service.SecurityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/security")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService service;

    @PostMapping
    public ResponseEntity<ResponseDTO<Long>> create(SecurityCredentials securityCredentials) {
        return new ResponseEntity<>(service.create(securityCredentials), HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<ResponseDTO<SecurityCredentials>> getByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(service.getByEmail(email));
    }

    @PutMapping("/change-password")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid PasswordUpdateDTO updateDTO) {
        service.update(new SecurityCredentials(updateDTO));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/edit-role")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid RolesUpdateDTO updateDTO) {
        service.editRoles(updateDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO<List<SecurityCredentials>>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}