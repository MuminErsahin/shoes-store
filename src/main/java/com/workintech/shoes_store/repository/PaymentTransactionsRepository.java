package com.workintech.shoes_store.repository;

import com.workintech.shoes_store.entity.PaymentTransactions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentTransactionsRepository extends JpaRepository<PaymentTransactions, Long> {
}
