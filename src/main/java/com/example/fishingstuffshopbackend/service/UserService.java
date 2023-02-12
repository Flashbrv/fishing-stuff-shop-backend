package com.example.fishingstuffshopbackend.service;

import com.example.fishingstuffshopbackend.domain.Role;
import com.example.fishingstuffshopbackend.domain.User;

public interface UserService extends ServiceBase<User>{
    Role save(Role role);
    void addRoleToUser(String email, String roleName);
    void removeRoleFromUser(String email, String roleName);
}
