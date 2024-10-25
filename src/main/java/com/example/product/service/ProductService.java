package com.example.product.service;

import com.example.product.model.ProductRequest;
import com.example.product.model.ProductResponse;
import com.example.product.model.exception.ErrorDto;
import com.example.product.model.exception.ProductNotFoundException;
import com.example.product.model.exception.ResourceAlreadyExistsException;
import com.example.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private ProductRepository productRepository;
    private ProductMapper mapper;

    public List<ProductResponse> getProducts() {
        log.info("Get all products");
        return productRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public ProductResponse getProductDetails(Long id) {
        log.info("Get product details for id: [%s]".formatted(id));
        return mapper.toResponse(productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id [%s] not found".formatted(id))));
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest, String email) {
        log.info("Updating product with id: [%s]".formatted(id));

        var entity = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id [%s] not found".formatted(id)));

        if (!entity.getName().equals(productRequest.getName()))
            checkIfProductExists(productRequest.getName());

        var updatedEntity = productRepository.save(mapper.updateEntity(entity, productRequest, email));
        return mapper.toResponse(updatedEntity);
    }

    public ProductResponse createProduct(ProductRequest productRequest, String email) {
        checkIfProductExists(productRequest.getName());
        log.info("Creating product with name: [%s]".formatted(productRequest.getName()));

        var entity = productRepository.save(mapper.toEntity(productRequest, email));
        return mapper.toResponse(entity);
    }

    public void deleteProduct(Long id) {
        log.info("Deleting product with id: [%s]".formatted(id));

        var entity = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id [%s] not found".formatted(id)));

        productRepository.delete(entity);
    }

    public void checkIfProductExists(String name) {
        productRepository.findByName(name)
                .ifPresent(product -> {
                    var message = "Product with name [%s] already exists".formatted(name);
                    log.error(message);
                    throw new ResourceAlreadyExistsException(message);
                });
    }
}
