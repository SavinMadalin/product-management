package com.example.product.service;

import com.example.product.model.CustomerEntity;
import com.example.product.model.CustomerDetails;
import com.example.product.model.CustomerRequest;
import com.example.product.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface SecurityMapper {

    Role toRole(String authority);

    @Mapping(expression = "java(encodePassword(customerRequest))", target = "password")
    CustomerEntity toCustomer(CustomerRequest customerRequest, Set<Role> authorities);

    CustomerDetails toCustomerDetails(CustomerEntity entity);

    default String encodePassword(CustomerRequest customerRequest) {
        var encoder = new BCryptPasswordEncoder();
        return encoder.encode(customerRequest.password());
    }
}
