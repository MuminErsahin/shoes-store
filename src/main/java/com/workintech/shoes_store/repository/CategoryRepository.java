package com.workintech.shoes_store.repository;

import com.workintech.shoes_store.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Categories,Long> {
}
