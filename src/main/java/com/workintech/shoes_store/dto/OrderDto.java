package com.workintech.shoes_store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDto {

    private Long id;
    private double totalAmount;
    private String address;
    private PaymentTransactionsDto paymentTransactionsDto;

}
