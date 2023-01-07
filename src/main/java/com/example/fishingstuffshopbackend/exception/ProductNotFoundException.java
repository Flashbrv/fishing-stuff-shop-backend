package com.example.fishingstuffshopbackend.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(long id) {
        super(String.format("Product with id=%d not found", id));
    }
}
