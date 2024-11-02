package com.workintech.shoes_store.controller;

import com.workintech.shoes_store.converter.ProductConverter;
import com.workintech.shoes_store.dto.ProductDto;
import com.workintech.shoes_store.entity.Brands;
import com.workintech.shoes_store.entity.Product;
import com.workintech.shoes_store.exceptions.ShoesStoreException;
import com.workintech.shoes_store.repository.BrandsRepository;
import com.workintech.shoes_store.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/products")
@Validated
public class ProductController {
    private  ProductService productService;
    private BrandsRepository brandsRepository;
    private ProductConverter productConverter;

    @Autowired
    public ProductController(ProductService productService,BrandsRepository brandsRepository,ProductConverter productConverter) {
        this.productService = productService;
        this.brandsRepository = brandsRepository;
        this.productConverter = productConverter;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    // ProductController.java
    @PostMapping
    public ResponseEntity<ProductDto> save(@RequestBody @Valid ProductDto productDto) {
        // Brand kontrolü ekle
        if (productDto.getBrandId() == null) {
            throw new ShoesStoreException("Brand ID is required", HttpStatus.BAD_REQUEST);
        }

        Optional<Brands> brand = brandsRepository.findById(productDto.getBrandId());
        if (!brand.isPresent()) {
            throw new ShoesStoreException("Brand not found with id: " + productDto.getBrandId(), HttpStatus.NOT_FOUND);
        }

        Product product = productConverter.toEntity(productDto);
        product.setBrands(brand.get());

        return new ResponseEntity<>(productService.save(productDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable @Min(1) Long id, @RequestBody @Valid Product product) {
        return ResponseEntity.ok(productService.update(id, product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable @Min(1) Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{productId}/categories/{categoryId}")
    public ResponseEntity<ProductDto> addCategoryToProduct(
            @PathVariable @Min(1) Long productId,
            @PathVariable @Min(1) Long categoryId) {
        return ResponseEntity.ok(productService.addCategoryToProduct(productId, categoryId));
    }
}