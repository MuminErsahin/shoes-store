package com.workintech.shoes_store.converter;

import com.workintech.shoes_store.dto.PaymentTransactionsDto;
import com.workintech.shoes_store.entity.PaymentTransactions;
import org.springframework.stereotype.Component;

@Component
public class PaymentTransactionsConverter {

    public PaymentTransactionsDto toDto(PaymentTransactions paymentTransactions) {
        if (paymentTransactions == null) {
            return null;
        }
        return new PaymentTransactionsDto(
                paymentTransactions.getId(),
                paymentTransactions.getName(),
                paymentTransactions.getPaymentMethod(),
                paymentTransactions.getAmount(),
                paymentTransactions.getStatus()

        );
    }

    public PaymentTransactions toEntity(PaymentTransactionsDto paymentTransactionsDto) {
        PaymentTransactions paymentTransactions = new PaymentTransactions();
        paymentTransactions.setId(paymentTransactionsDto.getId());
        paymentTransactions.setName(paymentTransactionsDto.getName());
        paymentTransactions.setPaymentMethod(paymentTransactionsDto.getPaymentMethod());
        paymentTransactions.setAmount(paymentTransactionsDto.getAmount());
        paymentTransactions.setStatus(paymentTransactionsDto.getStatus());
        return paymentTransactions;
    }
}
