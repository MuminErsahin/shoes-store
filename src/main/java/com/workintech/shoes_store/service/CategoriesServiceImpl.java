package com.workintech.shoes_store.service;

import com.workintech.shoes_store.converter.CategoriesConverter;
import com.workintech.shoes_store.dto.CategoriesDto;
import com.workintech.shoes_store.entity.Brands;
import com.workintech.shoes_store.entity.Categories;
import com.workintech.shoes_store.entity.Product;
import com.workintech.shoes_store.exceptions.ShoesStoreException;
import com.workintech.shoes_store.repository.CategoryRepository;
import com.workintech.shoes_store.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CategoriesServiceImpl implements CategoriesService {

    private CategoryRepository categoryRepository;
    private CategoriesConverter categoriesConverter;
    private ProductRepository productRepository;

    @Autowired
    public CategoriesServiceImpl(CategoryRepository categoryRepository, CategoriesConverter categoriesConverter, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.categoriesConverter = categoriesConverter;
        this.productRepository = productRepository;
    }

    @Override
    public CategoriesDto save(Categories categories) {
        if (categories == null || categories.getName() == null || categories.getName().trim().isEmpty()) {
            throw new ShoesStoreException("Category name cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        try {
            Categories savedCategories = categoryRepository.save(categories);
            return categoriesConverter.toDto(savedCategories);
        } catch (Exception e) {
            throw new ShoesStoreException("Error occurred while saving category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<CategoriesDto> findAll() {
        List<Categories> categoriesList = categoryRepository.findAll();
        if (categoriesList.isEmpty()) {
            throw new ShoesStoreException("No categories found", HttpStatus.NOT_FOUND);
        }

        List<CategoriesDto> categoriesDtoList = new ArrayList<>();
        for (Categories categories : categoriesList) {
            categoriesDtoList.add(categoriesConverter.toDto(categories));
        }
        return categoriesDtoList;
    }

    @Override
    public CategoriesDto getById(Long id) {
        if (id == null || id <= 0) {
            throw new ShoesStoreException("Invalid category ID", HttpStatus.BAD_REQUEST);
        }

        Optional<Categories> categoriesOptional = categoryRepository.findById(id);
        if (categoriesOptional.isPresent()) {
            return categoriesConverter.toDto(categoriesOptional.get());
        } else {
            throw new ShoesStoreException("Category not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public CategoriesDto update(Long id, Categories categories) {
        if (id == null || id <= 0) {
            throw new ShoesStoreException("Invalid category ID", HttpStatus.BAD_REQUEST);
        }

        if (categories == null || categories.getName() == null || categories.getName().trim().isEmpty()) {
            throw new ShoesStoreException("Category details cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        Optional<Categories> optionalCategories = categoryRepository.findById(id);
        if (optionalCategories.isPresent()) {
            Categories existingCategories = optionalCategories.get();
            existingCategories.setName(categories.getName());
            existingCategories.setDescription(categories.getDescription());

            try {
                Categories updatedCategory = categoryRepository.save(existingCategories);
                return categoriesConverter.toDto(updatedCategory);
            } catch (Exception e) {
                throw new ShoesStoreException("Error occurred while updating category", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            throw new ShoesStoreException("Category not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public CategoriesDto addProductToCategory(Long categoryId, Long productId) {
        if (categoryId == null || categoryId <= 0 || productId == null || productId <= 0) {
            throw new ShoesStoreException("Invalid category or product ID", HttpStatus.BAD_REQUEST);
        }

        Categories category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ShoesStoreException("Category not found with id: " + categoryId, HttpStatus.NOT_FOUND));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ShoesStoreException("Product not found with id: " + productId, HttpStatus.NOT_FOUND));

        try {
            category.getProducts().add(product);
            Categories savedCategory = categoryRepository.save(category);
            return categoriesConverter.toDto(savedCategory);
        } catch (Exception e) {
            throw new ShoesStoreException("Error occurred while adding product to category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public void deleteById(Long id) {
        if (id == null || id <= 0) {
            throw new ShoesStoreException("Invalid category ID", HttpStatus.BAD_REQUEST);
        }

        if (!categoryRepository.existsById(id)) {
            throw new ShoesStoreException("Category not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        try {
            categoryRepository.deleteById(id);
        } catch (Exception e) {
            throw new ShoesStoreException("Error occurred while deleting category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}