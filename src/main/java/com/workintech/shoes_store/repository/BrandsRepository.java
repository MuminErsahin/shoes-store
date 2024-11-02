package com.workintech.shoes_store.repository;

import com.workintech.shoes_store.entity.Brands;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandsRepository extends JpaRepository<Brands, Long> {
    Optional<Brands> findByName(String name);
}
