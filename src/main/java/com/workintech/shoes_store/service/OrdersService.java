package com.workintech.shoes_store.service;

import com.workintech.shoes_store.dto.OrderDto;
import com.workintech.shoes_store.entity.Orders;
import com.workintech.shoes_store.entity.Product;

import java.util.List;

public interface OrdersService {
    OrderDto save(Orders orders);
    List<OrderDto> findAll();
    OrderDto getById(Long id);
    OrderDto update(Long id, Orders orders);
    void deleteById(Long id);
}
