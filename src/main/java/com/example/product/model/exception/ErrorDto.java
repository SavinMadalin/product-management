package com.example.product.model.exception;

import lombok.Builder;

@Builder
public record ErrorDto(String message,
                       int errorCode,
                       String help) {
}
