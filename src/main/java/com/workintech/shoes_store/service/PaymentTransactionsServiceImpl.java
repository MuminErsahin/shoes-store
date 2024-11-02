package com.workintech.shoes_store.service;

import com.workintech.shoes_store.converter.PaymentTransactionsConverter;
import com.workintech.shoes_store.dto.PaymentTransactionsDto;
import com.workintech.shoes_store.entity.PaymentTransactions;
import com.workintech.shoes_store.entity.Product;
import com.workintech.shoes_store.exceptions.ShoesStoreException;
import com.workintech.shoes_store.repository.PaymentTransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentTransactionsServiceImpl implements PaymentTransactionsService {

    private final PaymentTransactionsRepository paymentTransactionsRepository;
    private final PaymentTransactionsConverter paymentTransactionsConverter;

    @Autowired
    public PaymentTransactionsServiceImpl(PaymentTransactionsRepository paymentTransactionsRepository,
                                          PaymentTransactionsConverter paymentTransactionsConverter) {
        this.paymentTransactionsRepository = paymentTransactionsRepository;
        this.paymentTransactionsConverter = paymentTransactionsConverter;
    }

    @Override
    public PaymentTransactionsDto save(PaymentTransactionsDto paymentTransactionsDto) {
        if (paymentTransactionsDto == null) {
            throw new ShoesStoreException("Payment transaction cannot be null", HttpStatus.BAD_REQUEST);
        }


        if (paymentTransactionsDto.getName() == null || paymentTransactionsDto.getName().trim().isEmpty()) {
            throw new ShoesStoreException("Payment transaction name cannot be empty", HttpStatus.BAD_REQUEST);
        }

        if (paymentTransactionsDto.getAmount() < 0) {
            throw new ShoesStoreException("Payment amount cannot be negative", HttpStatus.BAD_REQUEST);
        }

        try {
            PaymentTransactions savedPaymentTransactions = paymentTransactionsRepository.save(
                    paymentTransactionsConverter.toEntity(paymentTransactionsDto));
            return paymentTransactionsConverter.toDto(savedPaymentTransactions);
        } catch (Exception e) {
            throw new ShoesStoreException("Error occurred while saving payment transaction", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<PaymentTransactionsDto> findAll() {
        List<PaymentTransactions> transactions = paymentTransactionsRepository.findAll();
        if (transactions.isEmpty()) {
            throw new ShoesStoreException("No payment transactions found", HttpStatus.NOT_FOUND);
        }

        return transactions.stream()
                .map(paymentTransactionsConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public PaymentTransactionsDto getById(Long id) {
        if (id == null || id <= 0) {
            throw new ShoesStoreException("Invalid payment transaction ID", HttpStatus.BAD_REQUEST);
        }

        Optional<PaymentTransactions> paymentTransactionsOptional = paymentTransactionsRepository.findById(id);
        return paymentTransactionsOptional
                .map(paymentTransactionsConverter::toDto)
                .orElseThrow(() -> new ShoesStoreException("Payment transaction not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public PaymentTransactionsDto update(Long id, PaymentTransactions paymentTransactions) {
        if (id == null || id <= 0) {
            throw new ShoesStoreException("Invalid payment transaction ID", HttpStatus.BAD_REQUEST);
        }

        if (paymentTransactions == null) {
            throw new ShoesStoreException("Payment transaction details cannot be null", HttpStatus.BAD_REQUEST);
        }

        Optional<PaymentTransactions> paymentTransactionsOptional = paymentTransactionsRepository.findById(id);
        if (paymentTransactionsOptional.isPresent()) {
            PaymentTransactions existingTransaction = paymentTransactionsOptional.get();


            if (paymentTransactions.getName() != null && paymentTransactions.getName().trim().isEmpty()) {
                throw new ShoesStoreException("Payment transaction name cannot be empty", HttpStatus.BAD_REQUEST);
            }

            if (paymentTransactions.getAmount() < 0) {
                throw new ShoesStoreException("Payment amount cannot be negative", HttpStatus.BAD_REQUEST);
            }


            existingTransaction.setName(paymentTransactions.getName());
            existingTransaction.setAmount(paymentTransactions.getAmount());
            existingTransaction.setPaymentMethod(paymentTransactions.getPaymentMethod());
            existingTransaction.setStatus(paymentTransactions.getStatus());
            existingTransaction.setOrders(paymentTransactions.getOrders());

            try {
                PaymentTransactions updatedTransaction = paymentTransactionsRepository.save(existingTransaction);
                return paymentTransactionsConverter.toDto(updatedTransaction);
            } catch (Exception e) {
                throw new ShoesStoreException("Error occurred while updating payment transaction", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        throw new ShoesStoreException("Payment transaction not found with id: " + id, HttpStatus.NOT_FOUND);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null || id <= 0) {
            throw new ShoesStoreException("Invalid payment transaction ID", HttpStatus.BAD_REQUEST);
        }

        if (!paymentTransactionsRepository.existsById(id)) {
            throw new ShoesStoreException("Payment transaction not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        try {
            paymentTransactionsRepository.deleteById(id);
        } catch (Exception e) {
            throw new ShoesStoreException("Error occurred while deleting payment transaction", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}