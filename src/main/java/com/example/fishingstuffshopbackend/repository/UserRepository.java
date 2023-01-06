package com.example.fishingstuffshopbackend.repository;

import com.example.fishingstuffshopbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
