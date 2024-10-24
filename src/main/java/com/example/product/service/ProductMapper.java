package com.example.product.service;

import com.example.product.model.ProductEntity;
import com.example.product.model.ProductRequest;
import com.example.product.model.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductEntity toEntity(ProductRequest productRequest, String createdBy);
    ProductResponse toResponse(ProductEntity entity);

    @Mapping(target = "updatedBy", source = "updatedBy")
    @Mapping(target = "description", source = "productRequest.description")
    @Mapping(target = "price", source = "productRequest.price")
    @Mapping(target = "name", source = "productRequest.name")
    ProductEntity updateEntity(@MappingTarget ProductEntity productEntity, ProductRequest productRequest, String updatedBy);
}
