package com.example.fishingstuffshopbackend.exception;

public class ProductTypeNotFoundException extends RuntimeException {
    public ProductTypeNotFoundException(Long id) {
        super("Couldn't find product type with id=" + id);
    }
}
