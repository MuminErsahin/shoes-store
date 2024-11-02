package com.workintech.shoes_store.utils.security;



import lombok.Data;
import java.util.List;

@Data
public class AuthResponse {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String country;
    private List<String> roles;
}
