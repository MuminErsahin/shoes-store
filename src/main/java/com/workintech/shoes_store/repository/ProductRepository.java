package com.workintech.shoes_store.repository;

import com.workintech.shoes_store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
