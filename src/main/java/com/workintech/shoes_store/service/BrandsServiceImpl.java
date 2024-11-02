package com.workintech.shoes_store.service;

import com.workintech.shoes_store.converter.BrandsConverter;
import com.workintech.shoes_store.dto.BrandsDto;
import com.workintech.shoes_store.entity.Brands;
import com.workintech.shoes_store.exceptions.ShoesStoreException;
import com.workintech.shoes_store.repository.BrandsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class BrandsServiceImpl implements BrandsService {
    private BrandsRepository brandsRepository;
    private BrandsConverter brandsConverter;

    @Autowired
    public BrandsServiceImpl(BrandsRepository brandsRepository, BrandsConverter brandsConverter) {
        this.brandsRepository = brandsRepository;
        this.brandsConverter = brandsConverter;
    }

    @Override
    public BrandsDto save(Brands brands) {

        if (brands == null || brands.getName() == null || brands.getName().trim().isEmpty()) {
            throw new ShoesStoreException("Brand name cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        if (brands.getProducts() == null) {
            brands.setProducts(new ArrayList<>());
        }

        try {
            Brands savedBrands = brandsRepository.save(brands);
            return brandsConverter.toDto(savedBrands);
        } catch (Exception e) {
            throw new ShoesStoreException("Error occurred while saving brand", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<BrandsDto> findAll() {
        List<Brands> brandsList = brandsRepository.findAll();
        if (brandsList.isEmpty()) {
            throw new ShoesStoreException("No brands found", HttpStatus.NOT_FOUND);
        }

        List<BrandsDto> brandsDtoList = new ArrayList<>();
        for (Brands brand : brandsList) {
            brandsDtoList.add(brandsConverter.toDto(brand));
        }
        return brandsDtoList;
    }

    @Override
    public BrandsDto getById(Long id) {
        if (id == null || id <= 0) {
            throw new ShoesStoreException("Invalid brand ID", HttpStatus.BAD_REQUEST);
        }

        Optional<Brands> optionalBrand = brandsRepository.findById(id);
        if (optionalBrand.isPresent()) {
            return brandsConverter.toDto(optionalBrand.get());
        } else {
            throw new ShoesStoreException("Brand not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public BrandsDto update(Long id, Brands brands) {
        if (id == null || id <= 0) {
            throw new ShoesStoreException("Invalid brand ID", HttpStatus.BAD_REQUEST);
        }

        if (brands == null || brands.getName() == null || brands.getName().trim().isEmpty()) {
            throw new ShoesStoreException("Brand details cannot be null or empty", HttpStatus.BAD_REQUEST);
        }

        Optional<Brands> optionalBrand = brandsRepository.findById(id);
        if (optionalBrand.isPresent()) {
            Brands existingBrand = optionalBrand.get();
            existingBrand.setName(brands.getName());
            existingBrand.setDescription(brands.getDescription());

            try {
                Brands updatedBrand = brandsRepository.save(existingBrand);
                return brandsConverter.toDto(updatedBrand);
            } catch (Exception e) {
                throw new ShoesStoreException("Error occurred while updating brand", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            throw new ShoesStoreException("Brand not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteById(Long id) {
        if (id == null || id <= 0) {
            throw new ShoesStoreException("Invalid brand ID", HttpStatus.BAD_REQUEST);
        }

        if (!brandsRepository.existsById(id)) {
            throw new ShoesStoreException("Brand not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        try {
            brandsRepository.deleteById(id);
        } catch (Exception e) {
            throw new ShoesStoreException("Error occurred while deleting brand", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}