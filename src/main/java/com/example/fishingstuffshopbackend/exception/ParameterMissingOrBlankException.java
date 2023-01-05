package com.example.fishingstuffshopbackend.exception;

public class ParameterMissingOrBlankException extends RuntimeException {
    public ParameterMissingOrBlankException(String parameterName) {
        super("Required parameter " + parameterName + " is missing or blank");
    }
}
