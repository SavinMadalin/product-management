package com.example.product.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record CustomerRequest(
        @NotEmpty(message = "[email] parameter must not be null")
        @Email
        String email,
        @NotEmpty(message = "[password] parameter must not be null")
        String password,
        boolean isUserAdmin
) {
}
