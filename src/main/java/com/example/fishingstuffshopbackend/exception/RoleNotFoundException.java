package com.example.fishingstuffshopbackend.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(long id) {
        super(String.format("Role with id=%d not found", id));
    }

    public RoleNotFoundException(String name) {
        super(String.format("User with name:\"%s\" not found", name));
    }
}
