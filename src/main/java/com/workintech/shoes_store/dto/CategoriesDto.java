package com.workintech.shoes_store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoriesDto {
    private Long id;
    private String name;
    private String description;
    private List<ProductDto> productDtos;
}
