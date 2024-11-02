package com.workintech.shoes_store.service;

import com.workintech.shoes_store.converter.ProductConverter;
import com.workintech.shoes_store.dto.ProductDto;

import com.workintech.shoes_store.entity.Brands;
import com.workintech.shoes_store.entity.Categories;
import com.workintech.shoes_store.entity.Product;
import com.workintech.shoes_store.exceptions.ShoesStoreException;
import com.workintech.shoes_store.repository.BrandsRepository;
import com.workintech.shoes_store.repository.CategoryRepository;
import com.workintech.shoes_store.repository.ProductRepository;


import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductConverter productConverter;
    private BrandsRepository brandsRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductConverter productConverter,
                              BrandsRepository brandsRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.brandsRepository = brandsRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public ProductDto save(ProductDto productDto) {
        try {
            // Brand'i kontrol et
            if (productDto.getBrandId() == null) {
                throw new ShoesStoreException("Brand ID is required", HttpStatus.BAD_REQUEST);
            }

            Brands brand = brandsRepository.findById(productDto.getBrandId())
                    .orElseThrow(() -> new ShoesStoreException("Brand not found", HttpStatus.NOT_FOUND));

            // Product oluştur ve tüm alanları set et
            Product product = new Product();
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setStockQuantity(productDto.getStockQuantity());
            product.setSize(productDto.getSize());
            product.setColor(productDto.getColor());
            product.setPhoto(productDto.getPhoto());
            product.setBrands(brand);

            // Boş listeler oluştur
            product.setCategories(new ArrayList<>());
            product.setOrders(new ArrayList<>());

            // Kaydet
            Product savedProduct = productRepository.save(product);

            log.info("Product saved successfully with ID: {}", savedProduct.getId());

            return productConverter.toDto(savedProduct);

        } catch (Exception e) {
            log.error("Error saving product: ", e);
            throw new ShoesStoreException("Error saving product: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new ShoesStoreException("No products found", HttpStatus.NOT_FOUND);
        }

        return products.stream()
                .map(productConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getById(Long id) {
        if (id == null || id <= 0) {
            throw new ShoesStoreException("Invalid product ID", HttpStatus.BAD_REQUEST);
        }

        Optional<Product> productOptional = productRepository.findById(id);
        if (!productOptional.isPresent()) {
            throw new ShoesStoreException("Product not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        return productConverter.toDto(productOptional.get());
    }

    @Override
    @Transactional
    public ProductDto update(Long id, Product updatedProduct) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ShoesStoreException("Product not found with id: " + id, HttpStatus.NOT_FOUND));

        // Mevcut değerleri güncelle
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setStockQuantity(updatedProduct.getStockQuantity());
        existingProduct.setSize(updatedProduct.getSize());
        existingProduct.setColor(updatedProduct.getColor());
        existingProduct.setPhoto(updatedProduct.getPhoto());

        // Brand'i güncelle
        if (updatedProduct.getBrands() != null && updatedProduct.getBrands().getId() != null) {
            Brands brand = brandsRepository.findById(updatedProduct.getBrands().getId())
                    .orElseThrow(() -> new ShoesStoreException("Brand not found", HttpStatus.NOT_FOUND));
            existingProduct.setBrands(brand);
        }

        // Kaydet ve DTO'ya çevir
        Product savedProduct = productRepository.save(existingProduct);
        return productConverter.toDto(savedProduct);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null || id <= 0) {
            throw new ShoesStoreException("Invalid product ID", HttpStatus.BAD_REQUEST);
        }

        if (!productRepository.existsById(id)) {
            throw new ShoesStoreException("Product not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        try {
            productRepository.deleteById(id);
        } catch (Exception e) {
            throw new ShoesStoreException("Error occurred while deleting product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ProductDto addCategoryToProduct(Long productId, Long categoryId) {
        if (productId == null || productId <= 0 || categoryId == null || categoryId <= 0) {
            throw new ShoesStoreException("Invalid product or category ID", HttpStatus.BAD_REQUEST);
        }

        try {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ShoesStoreException("Product not found with id: " + productId, HttpStatus.NOT_FOUND));

            Categories category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ShoesStoreException("Category not found with id: " + categoryId, HttpStatus.NOT_FOUND));

            product.getCategories().add(category);
            Product updatedProduct = productRepository.save(product);
            return productConverter.toDto(updatedProduct);
        } catch (Exception e) {
            throw new ShoesStoreException("Error occurred while adding category to product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}