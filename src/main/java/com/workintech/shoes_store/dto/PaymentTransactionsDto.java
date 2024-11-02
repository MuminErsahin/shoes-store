package com.workintech.shoes_store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentTransactionsDto {
    private Long id;
    private String name;
    private String paymentMethod;
    private double amount;
    private String status;
}
