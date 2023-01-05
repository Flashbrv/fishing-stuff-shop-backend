package com.example.fishingstuffshopbackend.exception;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class RestErrorMessage {
    private final String timestamp = Timestamp.valueOf(LocalDateTime.now()).toString();
    private int status;
    private String error;
    private String message;
    private String path;

    public static RestResponseBuilder builder() {
        return new RestResponseBuilder();
    }
}
