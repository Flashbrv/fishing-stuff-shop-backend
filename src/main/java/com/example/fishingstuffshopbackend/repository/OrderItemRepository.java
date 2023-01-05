package com.example.fishingstuffshopbackend.repository;

import com.example.fishingstuffshopbackend.model.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Long> {
}
