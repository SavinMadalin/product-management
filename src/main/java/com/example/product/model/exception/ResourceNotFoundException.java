package com.example.product.model.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends GenericException {

    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
