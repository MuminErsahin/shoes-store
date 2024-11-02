package com.workintech.shoes_store.service;

import com.workintech.shoes_store.dto.PaymentTransactionsDto;
import com.workintech.shoes_store.entity.Orders;
import com.workintech.shoes_store.entity.PaymentTransactions;

import java.util.List;

public interface PaymentTransactionsService {
    PaymentTransactionsDto save(PaymentTransactionsDto paymentTransactionsDto);
    List<PaymentTransactionsDto> findAll();
    PaymentTransactionsDto getById(Long id);
    PaymentTransactionsDto update(Long id, PaymentTransactions paymentTransactions);
    void deleteById(Long id);
}
