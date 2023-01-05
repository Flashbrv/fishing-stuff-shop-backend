package com.example.fishingstuffshopbackend.repository;

import com.example.fishingstuffshopbackend.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
