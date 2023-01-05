package com.example.fishingstuffshopbackend.repository;

import com.example.fishingstuffshopbackend.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
