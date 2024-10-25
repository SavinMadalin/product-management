package com.example.product.controller;

import com.example.product.model.ProductRequest;
import com.example.product.model.ProductResponse;
import com.example.product.model.customer.CustomerEntity;
import com.example.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<ProductResponse>> getProducts() {
        List<ProductResponse> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ProductResponse> getProductDetails(@PathVariable Long id) {
        ProductResponse product = productService.getProductDetails(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/products/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest,
                                                         @AuthenticationPrincipal CustomerEntity customer) {
        ProductResponse product = productService.updateProduct(id, productRequest, customer.getEmail());
        return ResponseEntity.ok(product);
    }

    @PostMapping("/products")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest, @AuthenticationPrincipal CustomerEntity customer) {
        ProductResponse product = productService.createProduct(productRequest, customer.getEmail());
        return ResponseEntity.status(201).body(product);
    }

    @DeleteMapping("/products/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
