package com.example.fishingstuffshopbackend.repository;

import com.example.fishingstuffshopbackend.domain.Bucket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BucketRepository extends JpaRepository<Bucket, Long> {
}
