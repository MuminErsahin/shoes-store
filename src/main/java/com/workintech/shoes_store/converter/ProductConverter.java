package com.workintech.shoes_store.converter;

import com.workintech.shoes_store.dto.ProductDto;
import com.workintech.shoes_store.entity.Brands;
import com.workintech.shoes_store.entity.Product;
import com.workintech.shoes_store.repository.BrandsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ProductConverter {

    @Autowired
    private BrandsRepository brandsRepository;

    public Product toEntity(ProductDto productDto) {
        if (productDto == null) {
            return null;
        }

        Product product = new Product();

        if (productDto.getId() != null) {
            product.setId(productDto.getId());
        }

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setStockQuantity(productDto.getStockQuantity());
        product.setSize(productDto.getSize());
        product.setColor(productDto.getColor());
        product.setPhoto(productDto.getPhoto());

        if (productDto.getBrandId() != null) {

            Brands brand = brandsRepository.getReferenceById(productDto.getBrandId());
            product.setBrands(brand);
        }

        return product;
    }

    public ProductDto toDto(Product product) {
        if (product == null) {
            return null;
        }

        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        productDto.setStockQuantity(product.getStockQuantity());
        productDto.setSize(product.getSize());
        productDto.setColor(product.getColor());
        productDto.setPhoto(product.getPhoto());

        // Brand ID'yi ekle
        if (product.getBrands() != null) {
            productDto.setBrandId(product.getBrands().getId());
        }

        return productDto;
    }
}