package com.workintech.shoes_store.controller;

import com.workintech.shoes_store.entity.ERole;
import com.workintech.shoes_store.entity.Role;
import com.workintech.shoes_store.entity.Users;
import com.workintech.shoes_store.exceptions.ShoesStoreException;
import com.workintech.shoes_store.repository.RoleRepository;
import com.workintech.shoes_store.repository.UsersRepository;
import com.workintech.shoes_store.utils.security.AuthResponse;
import com.workintech.shoes_store.utils.security.request.LoginRequest;
import com.workintech.shoes_store.utils.security.request.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthController(UsersRepository usersRepository,
                          PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        // Email kontrolü
        if (usersRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ShoesStoreException("Email already exists", HttpStatus.BAD_REQUEST);
        }

        try {
            // Yeni kullanıcı oluştur
            Users user = new Users();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setPhoneNumber(request.getPhoneNumber());
            user.setAddress(request.getAddress());
            user.setCity(request.getCity());
            user.setCountry(request.getCountry());

            // Default USER rolünü ata
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new ShoesStoreException("Default role not found", HttpStatus.INTERNAL_SERVER_ERROR));
            user.setRoles(Set.of(userRole));

            // Kullanıcıyı kaydet
            Users savedUser = usersRepository.save(user);

            // Response hazırla
            AuthResponse response = new AuthResponse();
            response.setId(savedUser.getId());
            response.setName(savedUser.getName());
            response.setEmail(savedUser.getEmail());
            response.setRoles(savedUser.getRoles().stream()
                    .map(role -> role.getName().name())
                    .toList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new ShoesStoreException("Registration failed: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Users user = usersRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ShoesStoreException("User not found", HttpStatus.NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ShoesStoreException("Invalid password", HttpStatus.UNAUTHORIZED);
        }

        AuthResponse response = new AuthResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setPhoneNumber(user.getPhoneNumber());
        response.setAddress(user.getAddress());
        response.setCity(user.getCity());
        response.setCountry(user.getCountry());
        response.setRoles(user.getRoles().stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toList()));

        return ResponseEntity.ok(response);
    }
}