package com.vention.stockmarket.controller;

import com.vention.stockmarket.dto.response.FavouriteCompanyDTO;
import com.vention.stockmarket.service.FavouriteCompaniesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/favourite-companies")
@RequiredArgsConstructor
public class FavouriteCompaniesController {

    private final FavouriteCompaniesService service;

    @PostMapping
    public ResponseEntity<?> addToFavourites(@RequestParam("username") String username,
                                             @RequestParam("company_symbol") String companySymbol) {
        service.addToFavorites(username, companySymbol);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllByUsername(@RequestParam("username") String username) {
        List<FavouriteCompanyDTO> all = service.getAllByUsername(username);
        return ResponseEntity.ok(all);
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam("username") String username,
                                    @RequestParam("company_symbol") String companyName) {
        service.delete(username, companyName);
        return ResponseEntity.ok().build();
    }

}
