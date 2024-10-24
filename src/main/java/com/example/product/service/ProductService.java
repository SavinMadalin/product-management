package com.example.product.service;

import com.example.product.model.ProductRequest;
import com.example.product.model.ProductResponse;
import com.example.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.metrics.data.RepositoryMetricsAutoConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ProductResponse getProductDetails(Long id) {
        return mapper.toResponse(productRepository.findById(id).get());
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        var entity = productRepository.findById(id).get();
        var updatedEntity = productRepository.save(mapper.updateEntity(entity, productRequest, "user"));
        return mapper.toResponse(updatedEntity);
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        var entity = productRepository.save(mapper.toEntity(productRequest, "user"));
        return mapper.toResponse(entity);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
