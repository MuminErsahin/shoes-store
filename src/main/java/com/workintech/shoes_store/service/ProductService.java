package com.workintech.shoes_store.service;

import com.workintech.shoes_store.dto.ProductDto;
import com.workintech.shoes_store.entity.Product;

import java.util.List;

public interface ProductService {

    ProductDto save(ProductDto productDto);

    List<ProductDto> findAll();

    ProductDto getById(Long id);

    ProductDto update(Long id, Product product);

    void deleteById(Long id);

    ProductDto addCategoryToProduct(Long productId, Long categoryId);
}
