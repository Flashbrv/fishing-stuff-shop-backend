package com.example.fishingstuffshopbackend.repository;

import com.example.fishingstuffshopbackend.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
