package com.example.fishingstuffshopbackend.controller;

import com.example.fishingstuffshopbackend.exception.CategoryNotFoundException;
import com.example.fishingstuffshopbackend.exception.RestErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandlerController extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    ResponseEntity<?> responseStatusExceptionHandler(ResponseStatusException ex, WebRequest request) {
        log.error(ex.getReason(), ex);
        return handleResponseStatusException(ex, request);
    }

    @ExceptionHandler({CategoryNotFoundException.class})
    ResponseEntity<?> notFoundExceptionHandler(Exception ex, WebRequest request) {
        log.error(ex.getLocalizedMessage(), ex);
        return handleNotFoundException(ex, request);
    }

    //@ExceptionHandler({ParameterMissingOrBlankException.class})
    @ResponseStatus(code = BAD_REQUEST)
    ResponseEntity<?> parameterMissingOrBlankExceptionHandler(Exception ex, WebRequest request) {
        log.error(ex.getLocalizedMessage(), ex);
        return handleBadRequestException(ex, request);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<?> otherExceptionsHandler(Exception ex, WebRequest request) {
        log.error(ex.getLocalizedMessage(), ex);
        return handleEveryException(ex, request);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        ResponseEntity<?> responseEntity;
        if (!status.isError()) {
            responseEntity = handleStatusException(ex, status, request);
        } else if (INTERNAL_SERVER_ERROR.equals(status)) {
            request.setAttribute("javax.servlet.error.exception", ex, 0);
            responseEntity = handleEveryException(ex, request);
        } else {
            responseEntity = handleEveryException(ex, request);
        }
        return (ResponseEntity<Object>) responseEntity;
    }

    protected ResponseEntity<RestErrorMessage> handleResponseStatusException(ResponseStatusException ex,
                                                                             WebRequest request) {
        return RestErrorMessage.builder()
                .exception(ex)
                .path(getPath(request))
                .entity();
    }

    protected ResponseEntity<RestErrorMessage> handleStatusException(Exception ex,
                                                                     HttpStatus status,
                                                                     WebRequest request) {
        return RestErrorMessage.builder()
                .status(status)
                .message("Execution halted")
                .path(getPath(request))
                .entity();
    }

    protected ResponseEntity<RestErrorMessage> handleNotFoundException(Exception ex, WebRequest request) {
        return RestErrorMessage.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getLocalizedMessage())
                .path(getPath(request))
                .entity();
    }

    protected ResponseEntity<RestErrorMessage> handleBadRequestException(Exception ex, WebRequest request) {
        return RestErrorMessage.builder()
                .status(BAD_REQUEST)
                .message(ex.getLocalizedMessage())
                .path(getPath(request))
                .entity();
    }

    protected ResponseEntity<RestErrorMessage> handleEveryException(Exception ex, WebRequest request) {
        return RestErrorMessage.builder()
                .status(INTERNAL_SERVER_ERROR)
                .message(ex.getLocalizedMessage())
                .path(getPath(request))
                .entity();
    }

    private String getPath(WebRequest request) {
        return request.getDescription(false).substring(4);
    }
}
