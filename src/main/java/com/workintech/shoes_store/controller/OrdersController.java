package com.workintech.shoes_store.controller;

import com.workintech.shoes_store.dto.OrderDto;
import com.workintech.shoes_store.entity.Orders;
import com.workintech.shoes_store.service.OrdersService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/v1/orders")
@Validated
public class OrdersController {
    private final OrdersService ordersService;

    @Autowired
    public OrdersController(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    @PostMapping
    public ResponseEntity<OrderDto> save(@RequestBody @Valid Orders orders) {
        return new ResponseEntity<>(ordersService.save(orders), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> findAll() {
        return ResponseEntity.ok(ordersService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getById(@PathVariable @Min(1) Long id) {
        return ResponseEntity.ok(ordersService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> update(@PathVariable @Min(1) Long id, @RequestBody @Valid Orders orders) {
        return ResponseEntity.ok(ordersService.update(id, orders));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable @Min(1) Long id) {
        ordersService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}