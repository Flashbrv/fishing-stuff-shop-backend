package com.example.fishingstuffshopbackend.repository;

import com.example.fishingstuffshopbackend.model.BucketItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketItemRepository extends JpaRepository<BucketItem, Long> {
}
