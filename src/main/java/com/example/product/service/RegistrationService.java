package com.example.product.service;

import com.example.product.model.customer.CustomerDetails;
import com.example.product.model.customer.CustomerRequest;
import com.example.product.model.customer.Role;
import com.example.product.model.exception.ResourceAlreadyExistsException;
import com.example.product.repository.CustomerRepository;
import com.example.product.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class RegistrationService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final SecurityMapper securityMapper;

    public CustomerDetails registerCustomer(CustomerRequest customerRequest) {
        customerRepository.findByEmail(customerRequest.email())
                .ifPresent(c -> {
                    var message = "Customer with email: %s already exists ".formatted(customerRequest.email());
                    log.info(message);
                    throw new ResourceAlreadyExistsException(message);
                });

        String role = customerRequest.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER";
        Role userRole = roleRepository.findByAuthority(role)
                .orElse(roleRepository.save(securityMapper.toRole(role)));

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        var newCustomer = customerRepository.save(securityMapper.toCustomer(customerRequest, authorities));

        return securityMapper.toCustomerDetails(newCustomer);
    }
}

