package com.example.product.model.exception;

import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends GenericException {

    public ProductNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
