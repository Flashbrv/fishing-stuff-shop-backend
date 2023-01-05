package com.example.fishingstuffshopbackend.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

public class RestResponseBuilder {
    private int status;
    private String error;
    private String message;
    private String path;
    public RestResponseBuilder status(int status) {
        this.status = status;
        return this;
    }
    public RestResponseBuilder status(HttpStatus status) {
        this.status = status.value();
        if (status.isError()) {
            this.error = status.getReasonPhrase();
        }
        return this;
    }
    public RestResponseBuilder error(String error) {
        this.error = error;
        return this;
    }
    public RestResponseBuilder exception(ResponseStatusException exception) {
        HttpStatus status = exception.getStatus();
        this.status = status.value();

        if (!Objects.requireNonNull(exception.getReason()).isBlank()) {
            this.message = exception.getReason();
        }

        if (status.isError()) {
            this.error = status.getReasonPhrase();
        }
        return this;
    }
    public RestResponseBuilder message(String message) {
        this.message = message;
        return this;
    }
    public RestResponseBuilder path(String path) {
        this.path = path;
        return this;
    }

    public String json() {
        String result = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            result = objectMapper.writeValueAsString(build());
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Need fix it for pretty response in ExceptionHandlerFilter
        }
        return result;
    }

    public RestErrorMessage build() {
        RestErrorMessage response = new RestErrorMessage();
        response.setStatus(status);
        response.setError(error);
        response.setMessage(message);
        response.setPath(path);
        return response;
    }
    public ResponseEntity<RestErrorMessage> entity() {
        return ResponseEntity.status(status).headers(HttpHeaders.EMPTY).body(build());
    }
}
