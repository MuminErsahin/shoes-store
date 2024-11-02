package com.workintech.shoes_store.converter;

import com.workintech.shoes_store.dto.PaymentTransactionsDto;
import com.workintech.shoes_store.dto.UsersDto;
import com.workintech.shoes_store.entity.PaymentTransactions;
import com.workintech.shoes_store.entity.Users;
import com.workintech.shoes_store.dto.OrderDto;
import com.workintech.shoes_store.entity.Orders;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsersConverter {

    private OrderConverter orderConverter;

    public UsersConverter(OrderConverter orderConverter) {
        this.orderConverter = orderConverter;
    }

    public UsersDto entityToDto(Users users) {
        if (users == null) {
            return null;
        }

        UsersDto usersDto = new UsersDto();
        usersDto.setId(users.getId());
        usersDto.setName(users.getName());
        usersDto.setEmail(users.getEmail());
        usersDto.setPhoneNumber(users.getPhoneNumber());
        usersDto.setOrderDtoList(
                users.getOrders() != null
                        ? users.getOrders().stream()
                        .map(orderConverter::toDto)
                        .collect(Collectors.toList())
                        : Collections.emptyList()
        );
        return usersDto;
    }

    public Users dtoToEntity(UsersDto usersDto) {
        if (usersDto == null) {
            return null;
        }

        Users users = new Users();
        users.setId(usersDto.getId());
        users.setName(usersDto.getName());
        users.setEmail(usersDto.getEmail());
        users.setPhoneNumber(usersDto.getPhoneNumber());
        if (usersDto.getOrderDtoList() != null) {
            List<Orders> orders = usersDto.getOrderDtoList().stream()
                    .map(orderConverter::toEntity)
                    .collect(Collectors.toList());
            users.setOrders(orders);
        }
        return users;
    }
}