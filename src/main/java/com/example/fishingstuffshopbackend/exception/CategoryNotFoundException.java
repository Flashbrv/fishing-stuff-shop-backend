package com.example.fishingstuffshopbackend.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(long id) {
        super(String.format("Category with id=%d not found", id));
    }
}
