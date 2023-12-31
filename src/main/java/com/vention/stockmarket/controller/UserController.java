package com.vention.stockmarket.controller;

import com.vention.stockmarket.domain.UserModel;
import com.vention.stockmarket.dto.request.UserUpdateDTO;
import com.vention.stockmarket.dto.response.ResponseDTO;
import com.vention.stockmarket.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "2. Users", description = "endpoints for different operations on users")
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<ResponseDTO<Long>> create(UserModel userModel) {
        return new ResponseEntity<>(service.create(userModel), HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ResponseDTO<UserModel>> getByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(service.getByUsername(username));
    }

    @PutMapping
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid UserUpdateDTO updateDTO) {
        service.update(new UserModel(updateDTO));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<List<UserModel>>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}