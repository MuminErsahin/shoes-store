package com.workintech.shoes_store.controller;

import com.workintech.shoes_store.dto.PaymentTransactionsDto;
import com.workintech.shoes_store.entity.PaymentTransactions;
import com.workintech.shoes_store.service.PaymentTransactionsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(
        origins = {"https://shoes-store-frontend-iota.vercel.app", "http://localhost:5173"},
        allowedHeaders = "*",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS}
)
@RestController
@RequestMapping("/api/v1/payment-transactions")
@Validated
public class PaymentTransactionsController {
    private final PaymentTransactionsService paymentTransactionsService;

    @Autowired
    public PaymentTransactionsController(PaymentTransactionsService paymentTransactionsService) {
        this.paymentTransactionsService = paymentTransactionsService;
    }

    @PostMapping
    public ResponseEntity<PaymentTransactionsDto> save(@RequestBody @Valid PaymentTransactionsDto dto) {
        return new ResponseEntity<>(paymentTransactionsService.save(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PaymentTransactionsDto>> findAll() {
        return ResponseEntity.ok(paymentTransactionsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentTransactionsDto> getById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(paymentTransactionsService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentTransactionsDto> update(
            @PathVariable @Min(1) Long id,
            @RequestBody @Valid PaymentTransactions paymentTransactions) {
        return ResponseEntity.ok(paymentTransactionsService.update(id, paymentTransactions));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @Min(1) Long id) {
        paymentTransactionsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}