package com.example.product.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotEmpty(message = "[name] parameter is mandatory")
    private String name;
    private String description;
    @NotEmpty(message = "[price] parameter is mandatory")
    private String price;
}
