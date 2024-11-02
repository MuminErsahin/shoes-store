package com.workintech.shoes_store.service;


import com.workintech.shoes_store.converter.OrderConverter;
import com.workintech.shoes_store.converter.PaymentTransactionsConverter;
import com.workintech.shoes_store.dto.OrderDto;
import com.workintech.shoes_store.dto.PaymentTransactionsDto;
import com.workintech.shoes_store.entity.Orders;
import com.workintech.shoes_store.entity.Users;
import com.workintech.shoes_store.exceptions.ShoesStoreException;
import com.workintech.shoes_store.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl implements OrdersService {
    private OrdersRepository ordersRepository;
    private OrderConverter orderConverter;
    private PaymentTransactionsService paymentTransactionsService;
    private PaymentTransactionsConverter paymentTransactionsConverter;
    private UsersService usersService;

    @Autowired
    public OrdersServiceImpl(OrdersRepository ordersRepository, OrderConverter orderConverter,
                             PaymentTransactionsService paymentTransactionsService, PaymentTransactionsConverter paymentTransactionsConverter,
                             UsersService usersService) {
        this.ordersRepository = ordersRepository;
        this.orderConverter = orderConverter;
        this.paymentTransactionsService = paymentTransactionsService;
        this.paymentTransactionsConverter = paymentTransactionsConverter;
        this.usersService = usersService;
    }

    @Override
    public OrderDto save(Orders orders) {
        if (orders == null) {
            throw new ShoesStoreException("Order cannot be null", HttpStatus.BAD_REQUEST);
        }


        if (orders.getUsers() == null || orders.getUsers().getId() == null) {
            throw new ShoesStoreException("User information is required for order", HttpStatus.BAD_REQUEST);
        }

        Users user = usersService.getById(orders.getUsers().getId());
        if (user == null) {
            throw new ShoesStoreException("User not found with id: " + orders.getUsers().getId(), HttpStatus.NOT_FOUND);
        }
        orders.setUsers(user);


        if (orders.getPaymentTransactions() == null || orders.getPaymentTransactions().getId() == null) {
            throw new ShoesStoreException("Payment information is required for order", HttpStatus.BAD_REQUEST);
        }

        PaymentTransactionsDto payment = paymentTransactionsService.getById(orders.getPaymentTransactions().getId());
        if (payment == null) {
            throw new ShoesStoreException("Payment not found with id: " + orders.getPaymentTransactions().getId(), HttpStatus.NOT_FOUND);
        }
        orders.setPaymentTransactions(paymentTransactionsConverter.toEntity(payment));

        try {
            Orders savedOrders = ordersRepository.save(orders);
            return orderConverter.toDto(savedOrders);
        } catch (Exception e) {
            throw new ShoesStoreException("Error occurred while saving order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<OrderDto> findAll() {
        List<Orders> ordersList = ordersRepository.findAll();
        if (ordersList.isEmpty()) {
            throw new ShoesStoreException("No orders found", HttpStatus.NOT_FOUND);
        }

        return ordersList.stream()
                .map(order -> orderConverter.toDto(order))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto getById(Long id) {
        if (id == null || id <= 0) {
            throw new ShoesStoreException("Invalid order ID", HttpStatus.BAD_REQUEST);
        }

        Optional<Orders> optionalOrder = ordersRepository.findById(id);
        return optionalOrder.map(order -> orderConverter.toDto(order))
                .orElseThrow(() -> new ShoesStoreException("Order not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public OrderDto update(Long id, Orders orders) {
        if (id == null || id <= 0) {
            throw new ShoesStoreException("Invalid order ID", HttpStatus.BAD_REQUEST);
        }

        if (orders == null) {
            throw new ShoesStoreException("Order details cannot be null", HttpStatus.BAD_REQUEST);
        }

        Optional<Orders> optionalOrder = ordersRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Orders existingOrder = optionalOrder.get();


            if (orders.getTotalAmount() < 0) {
                throw new ShoesStoreException("Total amount cannot be negative", HttpStatus.BAD_REQUEST);
            }


            if (orders.getAddress() != null && orders.getAddress().trim().isEmpty()) {
                throw new ShoesStoreException("Address cannot be empty", HttpStatus.BAD_REQUEST);
            }

            existingOrder.setTotalAmount(orders.getTotalAmount());
            existingOrder.setAddress(orders.getAddress());
            existingOrder.setPaymentTransactions(orders.getPaymentTransactions());

            try {
                Orders savedOrder = ordersRepository.save(existingOrder);
                return orderConverter.toDto(savedOrder);
            } catch (Exception e) {
                throw new ShoesStoreException("Error occurred while updating order", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        throw new ShoesStoreException("Order not found with id: " + id, HttpStatus.NOT_FOUND);
    }

    @Override
    public void deleteById(Long id) {
        if (id == null || id <= 0) {
            throw new ShoesStoreException("Invalid order ID", HttpStatus.BAD_REQUEST);
        }

        if (!ordersRepository.existsById(id)) {
            throw new ShoesStoreException("Order not found with id: " + id, HttpStatus.NOT_FOUND);
        }

        try {
            ordersRepository.deleteById(id);
        } catch (Exception e) {
            throw new ShoesStoreException("Error occurred while deleting order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}