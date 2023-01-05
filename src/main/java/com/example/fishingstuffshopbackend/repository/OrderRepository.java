package com.example.fishingstuffshopbackend.repository;

import com.example.fishingstuffshopbackend.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
