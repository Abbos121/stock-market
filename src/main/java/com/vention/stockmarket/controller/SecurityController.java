package com.vention.stockmarket.controller;

import com.vention.stockmarket.model.SecurityModel;
import com.vention.stockmarket.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class SecurityController {

    private final SecurityService service;

    @PostMapping
    public ResponseEntity<?> create(SecurityModel securityModel) {
        return ResponseEntity.ok(service.create(securityModel));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PutMapping
    public ResponseEntity<?> update(SecurityModel securityModel) {
        service.update(securityModel);
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