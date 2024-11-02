package com.workintech.shoes_store.service;

import com.workintech.shoes_store.dto.UsersDto;
import com.workintech.shoes_store.entity.Users;

import java.util.List;

public interface UsersService {

    UsersDto save(Users users);
    List<UsersDto> findAll();
    Users getById(Long id);
    UsersDto getByID(Long id);
    UsersDto update(Long id, Users users);
    void deleteById(Long id);
    Users findByEmail(String email);
}
