package com.workintech.shoes_store.dto;

import com.workintech.shoes_store.entity.Brands;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private String size;
    private String color;
    private String photo;
    private Long brandId;
}
