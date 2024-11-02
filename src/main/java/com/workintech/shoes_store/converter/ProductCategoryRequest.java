package com.workintech.shoes_store.converter;

import com.workintech.shoes_store.entity.Categories;
import com.workintech.shoes_store.entity.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Data
public class ProductCategoryRequest {

    private Categories categories;
    private List<Product> productList;

}
