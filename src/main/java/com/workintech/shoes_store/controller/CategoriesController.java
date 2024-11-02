package com.workintech.shoes_store.controller;

import com.workintech.shoes_store.converter.ProductCategoryRequest;
import com.workintech.shoes_store.dto.CategoriesDto;
import com.workintech.shoes_store.entity.Categories;
import com.workintech.shoes_store.entity.Product;
import com.workintech.shoes_store.service.CategoriesService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/categories")
@Validated
public class CategoriesController {
    private final CategoriesService categoriesService;

    @Autowired
    public CategoriesController(CategoriesService categoriesService) {
        this.categoriesService = categoriesService;
    }

    @PostMapping
    public ResponseEntity<CategoriesDto> save(@RequestBody @Valid Categories categories) {
        return new ResponseEntity<>(categoriesService.save(categories), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CategoriesDto>> findAll() {
        return ResponseEntity.ok(categoriesService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriesDto> getById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(categoriesService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriesDto> update(
            @PathVariable @Min(1) Long id,
            @RequestBody @Valid Categories categories) {
        return ResponseEntity.ok(categoriesService.update(id, categories));
    }

    @PostMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<CategoriesDto> addProductToCategory(
            @PathVariable @Min(1) Long categoryId,
            @PathVariable @Min(1) Long productId) {
        return ResponseEntity.ok(categoriesService.addProductToCategory(categoryId, productId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @Min(1) Long id) {
        categoriesService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}