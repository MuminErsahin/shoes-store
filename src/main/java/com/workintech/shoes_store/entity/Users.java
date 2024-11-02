package com.workintech.shoes_store.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users", schema = "public")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name = "name")
    @NotNull(message = "Username can not be null")
    @Size(max = 50 )
    private String name;

    @Column(name = "email")
    @NotNull(message = "email can not be null")
    @Size(max = 50 )
    private String email;

    @Column(name = "password")
    @NotNull(message = "password can not be null")
    @Size(max = 150)
    private String password;

    @Column(name = "phone_number")
    @NotNull(message = "phoneNumber can not be null")
    @Size(max = 50 )
    private String phoneNumber;

    @Column(name = "address")
    @NotNull(message = "address can not be null")
    private String address;

    @Column(name = "city")
    @NotNull(message = "city can not be null")
    @Size(max = 50 )
    private String city;

    @Column(name = "country")
    @NotNull(message = "country can not be null")
    @Size(max = 50 )
    private String country;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "users")
    private List<Orders> orders = new ArrayList<>();


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    public void add(Orders order){
        orders.add(order);
    }
}
