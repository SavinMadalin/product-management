package com.example.product.service;

import com.example.product.model.ProductRequest;
import com.example.product.model.ProductResponse;
import com.example.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getProducts() {
    }

    public ProductResponse getProductDetails(Long id) {
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
    }

    public void deleteProduct(Long id) {
    }
}
