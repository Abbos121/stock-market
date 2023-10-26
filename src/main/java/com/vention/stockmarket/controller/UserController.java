package com.vention.stockmarket.controller;

import com.vention.stockmarket.domain.UserModel;
import com.vention.stockmarket.dto.request.UserUpdateDTO;
import com.vention.stockmarket.service.UserService;
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
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<?> create(UserModel userModel) {
        return ResponseEntity.ok(service.create(userModel));
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable("username") String username) {
        return ResponseEntity.ok(service.getByUsername(username));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody @Valid UserUpdateDTO updateDTO) {
        service.update(new UserModel(updateDTO));
        return ResponseEntity.ok().build();
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
