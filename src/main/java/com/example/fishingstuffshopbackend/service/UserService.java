package com.example.fishingstuffshopbackend.service;

import com.example.fishingstuffshopbackend.domain.Role;
import com.example.fishingstuffshopbackend.domain.User;

public interface UserService extends ServiceBase<User>{
    User findByEmail(String email);
    Role save(Role role);
    void addRoleToUser(String email, String roleName);
    void removeRoleFromUser(String email, String roleName);
    String signUpUser(User user);
    String confirmEmail(String token);
}
