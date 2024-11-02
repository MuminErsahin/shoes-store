package com.workintech.shoes_store.service;

import com.workintech.shoes_store.dto.BrandsDto;
import com.workintech.shoes_store.entity.Brands;

import java.util.List;

public interface BrandsService {
    BrandsDto save(Brands brands);
    List<BrandsDto> findAll();
    BrandsDto getById(Long id);
    BrandsDto update(Long id, Brands Brands);
    void deleteById(Long id);
}
