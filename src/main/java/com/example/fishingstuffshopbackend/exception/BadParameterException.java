package com.example.fishingstuffshopbackend.exception;

public class BadParameterException extends RuntimeException {
    public BadParameterException(String argName, Object value) {
        super(String.format("Value=%s for parameter %s is incorrect", value, argName));
    }
}
