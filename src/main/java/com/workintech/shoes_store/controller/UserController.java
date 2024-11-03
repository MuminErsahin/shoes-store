package com.workintech.shoes_store.controller;

import com.workintech.shoes_store.dto.UsersDto;
import com.workintech.shoes_store.entity.Users;
import com.workintech.shoes_store.service.UsersService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
@Validated
public class UserController {
    private final UsersService usersService;

    @Autowired
    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public ResponseEntity<UsersDto> save(@RequestBody @Valid Users users) {
        return new ResponseEntity<>(usersService.save(users), HttpStatus.CREATED);
    }
    @GetMapping("/me")
    public ResponseEntity<Users> getCurrentUser(Principal principal) {
        Users user = usersService.findByEmail(principal.getName());
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<UsersDto>> findAll() {
        return ResponseEntity.ok(usersService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersDto> getById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(usersService.getByID(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsersDto> update(@PathVariable @Min(1) Long id, @RequestBody @Valid Users users) {
        return ResponseEntity.ok(usersService.update(id, users));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @Min(1) Long id) {
        usersService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}