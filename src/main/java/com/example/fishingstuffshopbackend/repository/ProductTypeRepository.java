package com.example.fishingstuffshopbackend.repository;

import com.example.fishingstuffshopbackend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductTypeRepository extends JpaRepository<Category, Long> {
}
