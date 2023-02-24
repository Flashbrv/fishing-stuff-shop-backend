package com.example.fishingstuffshopbackend.repository;

import com.example.fishingstuffshopbackend.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
