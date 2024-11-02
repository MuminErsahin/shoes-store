package com.workintech.shoes_store.converter;

import com.workintech.shoes_store.dto.CategoriesDto;
import com.workintech.shoes_store.dto.ProductDto;
import com.workintech.shoes_store.entity.Categories;
import com.workintech.shoes_store.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoriesConverter {
 @Autowired
    private  ProductConverter productConverter;

 public CategoriesDto toDto(Categories categories){
     if(categories == null){
         return null;
     }
     CategoriesDto categoriesDto = new CategoriesDto();
     categoriesDto.setId(categories.getId());
     categoriesDto.setName(categories.getName());
     categoriesDto.setDescription(categories.getDescription());
     List<ProductDto> productDtoList = new ArrayList<>();
     for (Product product : categories.getProducts()) {
         productDtoList.add(productConverter.toDto(product));
     }
     categoriesDto.setProductDtos(productDtoList);
     return categoriesDto;
 }

 public Categories toEntity(CategoriesDto categoriesDto){
     if(categoriesDto == null){
         return null;
     }
     Categories categories = new Categories();
     categories.setId(categoriesDto.getId());
     categories.setName(categoriesDto.getName());
     categories.setDescription(categoriesDto.getDescription());
     List<Product> productList = new ArrayList<>();
     for (ProductDto productDto : categoriesDto.getProductDtos()) {
         productList.add(productConverter.toEntity(productDto));
     }
     categories.setProducts(productList);
     return categories;
 }
}
