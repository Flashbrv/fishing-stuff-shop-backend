package com.example.fishingstuffshopbackend.exception;

public class SuchUserExistException extends RuntimeException {
    public SuchUserExistException(String email) {
        super("User with email " + email + " currently exist");
    }
}
