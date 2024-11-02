package com.workintech.shoes_store.converter;

import com.workintech.shoes_store.dto.OrderDto;
import com.workintech.shoes_store.dto.PaymentTransactionsDto;
import com.workintech.shoes_store.entity.Orders;
import com.workintech.shoes_store.entity.PaymentTransactions;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderConverter {

    private final PaymentTransactionsConverter paymentTransactionsConverter;

    public OrderConverter(PaymentTransactionsConverter paymentTransactionsConverter) {
        this.paymentTransactionsConverter = paymentTransactionsConverter;
    }


    public Orders toEntity(OrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }

        Orders orders = new Orders();
        orders.setId(orderDto.getId());
        orders.setTotalAmount(orderDto.getTotalAmount());
        orders.setAddress(orderDto.getAddress());



        PaymentTransactions paymentTransactions = paymentTransactionsConverter.toEntity(orderDto.getPaymentTransactionsDto()); // Güncellendi
        orders.setPaymentTransactions(paymentTransactions);

        return orders;
    }


    public OrderDto toDto(Orders orders) {
        if (orders == null) {
            return null;
        }

        OrderDto orderDto = new OrderDto();
        orderDto.setId(orders.getId());
        orderDto.setTotalAmount(orders.getTotalAmount());
        orderDto.setAddress(orders.getAddress());




        PaymentTransactions paymentTransactions = orders.getPaymentTransactions();
        PaymentTransactionsDto dtoPaymentTransactions = paymentTransactionsConverter.toDto(paymentTransactions);
        orderDto.setPaymentTransactionsDto(dtoPaymentTransactions);

        return orderDto;
    }
}
