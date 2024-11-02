package com.workintech.shoes_store.service;

import com.workintech.shoes_store.converter.UsersConverter;
import com.workintech.shoes_store.dto.UsersDto;
import com.workintech.shoes_store.entity.Users;
import com.workintech.shoes_store.exceptions.ShoesStoreException;
import com.workintech.shoes_store.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    private final UsersRepository usersRepository;
    private final UsersConverter usersConverter;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, UsersConverter usersConverter) {
        this.usersRepository = usersRepository;
        this.usersConverter = usersConverter;
    }

    @Override
    public UsersDto save(Users users) {
        if (users == null) {
            throw new ShoesStoreException("User cannot be null", HttpStatus.BAD_REQUEST);
        }

        try {
            Users savedUsers = usersRepository.save(users);
            return usersConverter.entityToDto(savedUsers);
        } catch (Exception e) {
            throw new ShoesStoreException("Error occurred while saving user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<UsersDto> findAll() {
        List<Users> users = usersRepository.findAll();
        if (users.isEmpty()) {
            throw new ShoesStoreException("No users found", HttpStatus.NOT_FOUND);
        }
        return users.stream()
                .map(usersConverter::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UsersDto getByID(Long id) {
        if (id == null || id <= 0) {
            throw new ShoesStoreException("Invalid user ID", HttpStatus.BAD_REQUEST);
        }

        Optional<Users> usersOptional = usersRepository.findById(id);
        return usersOptional.map(usersConverter::entityToDto)
                .orElseThrow(() -> new ShoesStoreException("User not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public Users getById(Long id) {
        if (id == null || id <= 0) {
            throw new ShoesStoreException("Invalid user ID", HttpStatus.BAD_REQUEST);
        }

        return usersRepository.findById(id)
                .orElseThrow(() -> new ShoesStoreException("User not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public UsersDto update(Long id, Users users) {
        if (id == null || id <= 0) {
            throw new ShoesStoreException("Invalid user ID", HttpStatus.BAD_REQUEST);
        }

        if (users == null) {
            throw new ShoesStoreException("User details cannot be null", HttpStatus.BAD_REQUEST);
        }

        Optional<Users> usersOptional = usersRepository.findById(id);
        if (usersOptional.isPresent()) {
            Users existingUser = usersOptional.get();
            existingUser.setName(users.getName());
            existingUser.setEmail(users.getEmail());
            existingUser.setPhoneNumber(users.getPhoneNumber());
            existingUser.setAddress(users.getAddress());
            existingUser.setCity(users.getCity());
            existingUser.setCountry(users.getCountry());

            try {
                Users updatedUser = usersRepository.save(existingUser);
                return usersConverter.entityToDto(updatedUser);
            } catch (Exception e) {
                throw new ShoesStoreException("Error occurred while updating user", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        throw new ShoesStoreException("User not found with id: " + id, HttpStatus.NOT_FOUND);
    }

    @Override
    public Users findByEmail(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new ShoesStoreException("User not found", HttpStatus.NOT_FOUND));
    }

    @Override
    public void deleteById(Long id) {
        if (id == null || id <= 0) {
            throw new ShoesStoreException("Invalid user ID", HttpStatus.BAD_REQUEST);
        }

        if (!usersRepository.existsById(id)) {
            throw new ShoesStoreException("User not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        try {
            usersRepository.deleteById(id);
        } catch (Exception e) {
            throw new ShoesStoreException("Error occurred while deleting user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}