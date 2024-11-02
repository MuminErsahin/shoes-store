package com.workintech.shoes_store.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name ="name")
    @NotNull(message = "name can not be null")
    @Size(max = 50 )
    private String name;

    @Column(name ="description")
    private String description;

    @Column(name ="price")
    @NotNull(message = "price can not be null")
    private double price;

    @Column(name ="stock_quantity")
    @NotNull(message = "stockQuantity can not be null")
    private int stockQuantity;

    @Column(name ="size")
    @NotNull(message = "size can not be null")
    @Size(max = 50 )
    private String size;

    @Column(name ="color")
    @NotNull(message = "color can not be null")
    @Size(max = 50 )
    private String color;

    @Column(name ="photo")
    @Size(max = 255 )
    private String photo;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "brands_id", nullable = false)
    private Brands brands;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "order_product",
            joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Orders> orders;

    @ManyToMany( cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Categories> categories;
}
