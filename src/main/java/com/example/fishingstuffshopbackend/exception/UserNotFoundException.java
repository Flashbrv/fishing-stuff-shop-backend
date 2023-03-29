package com.example.fishingstuffshopbackend.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(long id) {
        super(String.format("User with id=%d not founded", id));
    }

    public UserNotFoundException(String email) {
        super(String.format("User with email:\"%s\" not founded", email));
    }
}
