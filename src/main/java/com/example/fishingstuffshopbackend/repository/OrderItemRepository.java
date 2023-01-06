package com.example.fishingstuffshopbackend.repository;

import com.example.fishingstuffshopbackend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
