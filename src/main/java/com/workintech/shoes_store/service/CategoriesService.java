package com.workintech.shoes_store.service;

import com.workintech.shoes_store.dto.CategoriesDto;
import com.workintech.shoes_store.entity.Categories;

import java.util.List;

public interface CategoriesService {
    CategoriesDto save(Categories categories);
    List<CategoriesDto> findAll();
    CategoriesDto getById(Long id);
    CategoriesDto update(Long id, Categories categories);
    void deleteById(Long id);
    CategoriesDto addProductToCategory(Long categoryId, Long productId);
}
