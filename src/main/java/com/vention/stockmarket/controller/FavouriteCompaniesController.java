package com.vention.stockmarket.controller;

import com.vention.stockmarket.dto.response.FavouriteCompanyDTO;
import com.vention.stockmarket.service.FavouriteCompaniesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/favourite-companies")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "5. Favourite Companies", description = "endpoints for managing favourite list of companies")
public class FavouriteCompaniesController {

    private final FavouriteCompaniesService service;

    @PostMapping
    public ResponseEntity<HttpStatus> addToFavourites(@RequestParam("username") String username,
                                                      @RequestParam("company_symbol") String companySymbol) {
        service.addToFavorites(username, companySymbol);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FavouriteCompanyDTO>> getAllByUsername(@RequestParam("username") String username) {
        List<FavouriteCompanyDTO> all = service.getAllByUsername(username);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<HttpStatus> delete(@RequestParam("username") String username,
                                             @RequestParam("company_symbol") String companyName) {
        service.delete(username, companyName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
