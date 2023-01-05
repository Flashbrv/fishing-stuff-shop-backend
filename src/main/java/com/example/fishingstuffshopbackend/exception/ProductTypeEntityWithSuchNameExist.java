package com.example.fishingstuffshopbackend.exception;

public class ProductTypeEntityWithSuchNameExist extends RuntimeException {
    public ProductTypeEntityWithSuchNameExist(String name) {
        super("Product type with name " + name + " currently exist");
    }
}
