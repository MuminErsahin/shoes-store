package com.workintech.shoes_store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BrandsDto {
    private Long id;
    private String name;
    private String description;
    private List<ProductDto> productDto;
}
