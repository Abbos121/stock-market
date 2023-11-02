package com.vention.stockmarket.controller;

import com.vention.stockmarket.domain.UserModel;
import com.vention.stockmarket.dto.request.UserUpdateDTO;
import com.vention.stockmarket.dto.response.ResponseDTO;
import com.vention.stockmarket.service.UserService;
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
@RequestMapping("/user")
@RequiredArgsConstructor
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
    public ResponseEntity<ResponseDTO<List<UserModel>>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }
}