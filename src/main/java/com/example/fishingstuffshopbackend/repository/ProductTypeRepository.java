package com.example.fishingstuffshopbackend.repository;

import com.example.fishingstuffshopbackend.model.ProductTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<ProductTypeEntity, Long> {
}
