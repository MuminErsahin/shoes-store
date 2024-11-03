package com.workintech.shoes_store.controller;

import com.workintech.shoes_store.dto.BrandsDto;
import com.workintech.shoes_store.entity.Brands;
import com.workintech.shoes_store.service.BrandsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/brands")
@Validated
public class BrandsController {
    private final BrandsService brandsService;

    @Autowired
    public BrandsController(BrandsService brandsService) {
        this.brandsService = brandsService;
    }

    @PostMapping
    public ResponseEntity<BrandsDto> save(@RequestBody @Valid Brands brands) {
        return new ResponseEntity<>(brandsService.save(brands), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<BrandsDto>> findAll() {
        return ResponseEntity.ok(brandsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandsDto> getById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(brandsService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandsDto> update(
            @PathVariable @Min(1) Long id,
            @RequestBody @Valid Brands brands) {
        return ResponseEntity.ok(brandsService.update(id, brands));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @Min(1) Long id) {
        brandsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}