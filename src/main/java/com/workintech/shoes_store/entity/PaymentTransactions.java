package com.workintech.shoes_store.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "payment_transactions")
public class PaymentTransactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id")
    private Long id;

    @Column(name ="name")
    @NotNull(message = "name can not be null")
    @Size(max = 50 )
    private String name;

    @Column(name ="amount")
    @NotNull(message = "amount can not be null")
    private double amount;

    @Column(name ="payment_method")
    @NotNull(message = "paymentMethod can not be null")
    @Size(max = 50 )
    private String paymentMethod;

    @Column(name ="status")
    @NotNull(message = "status can not be null")
    @Size(max = 50 )
    private String status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Orders orders;
}
