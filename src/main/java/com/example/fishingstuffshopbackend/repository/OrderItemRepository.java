package com.example.fishingstuffshopbackend.repository;

import com.example.fishingstuffshopbackend.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
