package com.example.fishingstuffshopbackend.repository;

import com.example.fishingstuffshopbackend.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
