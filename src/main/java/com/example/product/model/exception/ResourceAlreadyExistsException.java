package com.example.product.model.exception;

import org.springframework.http.HttpStatus;

public class ResourceAlreadyExistsException extends GenericException {

    public ResourceAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
