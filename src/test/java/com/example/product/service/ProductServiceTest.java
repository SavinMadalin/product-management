package com.example.product.service;

import com.example.product.model.ProductRequest;
import com.example.product.model.exception.ResourceNotFoundException;
import com.example.product.model.exception.ResourceAlreadyExistsException;
import com.example.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(value = {"classpath:/db/cleanup.sql", "classpath:/db/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    private static final String PRODUCT_DEFAULT_NAME = "book";
    private static final String DEFAULT_EMAIL = "user@db.com";
    private static final Long DEFAULT_ID = 234L;

    @Test
    public void serviceNotNullTest() {assertNotNull(productService);}

    @Test
    public void getProductsTest() {
        var products = productService.getProducts();
        assertNotNull(products);
        assertEquals(2, products.size());
        assertTrue(products.stream()
                .anyMatch(p -> PRODUCT_DEFAULT_NAME.equals(p.getName())));
    }

    @Test
    public void getProductDetailsTest() {
        var product = productService.getProductDetails(DEFAULT_ID);
        assertNotNull(product);
        assertEquals(PRODUCT_DEFAULT_NAME, product.getName());
    }

    @Test
    public void getProductsDetails_shouldReturn404NotFound() {
        var exception = assertThrows(ResourceNotFoundException.class,
                () -> productService.getProductDetails(3L));
        assertEquals("Product with id [3] not found", exception.getMessage());
    }

    @Test
    public void createProductTest() {
        var productName = "newProduct";
        var productRequest = ProductRequest.builder()
                .name(productName)
                .price("434").build();

        var product = productService.createProduct(productRequest, DEFAULT_EMAIL);
        assertNotNull(product);
        assertEquals(productName, product.getName());

        // verify that the product was inserted into the database
        var entity = productRepository.findByName(productName);
        assertEquals(productName, entity.get().getName());
    }

    @Test
    public void createProduct_shouldReturnConflict() {
        var productRequest = ProductRequest.builder()
                .name(PRODUCT_DEFAULT_NAME)
                .price("434").build();

        var exception = assertThrows(ResourceAlreadyExistsException.class,
                () -> productService.createProduct(productRequest, DEFAULT_EMAIL));

        assertEquals("Product with name [book] already exists", exception.getMessage());
    }

    @Test
    public void updateProductTest() {
        var productName = "updatedProduct";
        var productRequest = ProductRequest.builder()
                .name(productName)
                .price("434").build();

        var product = productService.updateProduct(DEFAULT_ID, productRequest, DEFAULT_EMAIL);
        assertNotNull(product);
        assertEquals(productName, product.getName());

        // verify that the product was updated into the database
        var entity = productRepository.findByName(productName);
        assertEquals(productName, entity.get().getName());
        assertTrue(productRepository.findByName(PRODUCT_DEFAULT_NAME).isEmpty());
    }

    @Test
    public void updateProduct_shouldReturnConflict() {
        var productRequest = ProductRequest.builder()
                .name(PRODUCT_DEFAULT_NAME)
                .price("434").build();

        var exception = assertThrows(ResourceAlreadyExistsException.class,
                () -> productService.updateProduct(123L, productRequest, DEFAULT_EMAIL));

        assertEquals("Product with name [book] already exists", exception.getMessage());
    }

    @Test
    public void updateProduct_shouldReturn404NotFound() {
        var productRequest = ProductRequest.builder()
                .name("updatedProduct")
                .build();

        var exception = assertThrows(ResourceNotFoundException.class,
                () -> productService.updateProduct(555L, productRequest, DEFAULT_EMAIL));

        assertEquals("Product with id [555] not found", exception.getMessage());
    }

    @Test
    public void deleteProductTest() {
        //verify that the product exists
        assertNotNull(productRepository.findById(DEFAULT_ID));

        //delete product
        productService.deleteProduct(DEFAULT_ID);

        // verify that the product was deleted from the database
        var entity = productRepository.findById(DEFAULT_ID);
        assertTrue(entity.isEmpty());
    }
}
