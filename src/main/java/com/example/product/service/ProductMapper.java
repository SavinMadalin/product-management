package com.example.product.service;

import com.example.product.model.ProductEntity;
import com.example.product.model.ProductRequest;
import com.example.product.model.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface ProductMapper {

    ProductEntity toEntity(ProductRequest productRequest, String createdBy);
    ProductResponse toResponse(ProductEntity entity);

    @Mapping(source = "updatedBy", target = "updatedBy")
    @Mapping(source = "description", target = "productRequest.description")
    @Mapping(source = "price", target = "productRequest.price")
    @Mapping(source = "name", target = "productRequest.name")
    ProductEntity updateEntity(@MappingTarget ProductEntity productEntity, ProductRequest productRequest, String updatedBy);
}
