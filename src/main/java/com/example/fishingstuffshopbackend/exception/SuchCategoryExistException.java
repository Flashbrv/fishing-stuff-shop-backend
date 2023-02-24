package com.example.fishingstuffshopbackend.exception;

public class SuchCategoryExistException extends RuntimeException {
    public SuchCategoryExistException(String title) {
        super("Category with title " + title + " currently exist");
    }
}
