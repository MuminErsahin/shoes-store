package com.workintech.shoes_store.repository;

import com.workintech.shoes_store.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders,Long> {
}
