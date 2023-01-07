package com.example.fishingstuffshopbackend.exception;

public class SuchCategoryExist extends RuntimeException {
    public SuchCategoryExist(String name) {
        super("Category with title " + name + " currently exist");
    }
}
