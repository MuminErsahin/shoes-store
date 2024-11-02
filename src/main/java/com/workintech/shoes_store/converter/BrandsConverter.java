package com.workintech.shoes_store.converter;

import com.workintech.shoes_store.dto.BrandsDto;
import com.workintech.shoes_store.dto.ProductDto;
import com.workintech.shoes_store.entity.Brands;
import com.workintech.shoes_store.entity.Product;
import com.workintech.shoes_store.repository.BrandsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BrandsConverter {
    @Autowired
    private ProductConverter productConverter;

    public BrandsDto toDto(Brands brands) {
        if (brands == null) {
            return null;
        }

        BrandsDto brandsDto = new BrandsDto();
        brandsDto.setId(brands.getId());
        brandsDto.setName(brands.getName());
        brandsDto.setDescription(brands.getDescription());


        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : brands.getProducts()) {
            productDtoList.add(productConverter.toDto(product));
        }
        brandsDto.setProductDto(productDtoList);

        return brandsDto;
    }

    public Brands toEntity(BrandsDto brandsDto) {
        if (brandsDto == null) {
            return null;
        }

        Brands brands = new Brands();
        brands.setId(brandsDto.getId());
        brands.setName(brandsDto.getName());
        brands.setDescription(brandsDto.getDescription());

        List<Product> productList = new ArrayList<>();
        for (ProductDto productDto : brandsDto.getProductDto()) {
            productList.add(productConverter.toEntity(productDto));
        }
        brands.setProducts(productList);

        return brands;
    }
}
