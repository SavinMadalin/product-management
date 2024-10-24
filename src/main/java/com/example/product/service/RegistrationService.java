package com.example.product.service;

import com.example.product.model.Customer;
import com.example.product.model.CustomerRequest;
import com.example.product.model.Role;
import com.example.product.repository.CustomerRepository;
import com.example.product.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final SecurityMapper securityMapper;

    public Customer registerCustomer(CustomerRequest customerRequest) {
        String role = Boolean.TRUE.equals(customerRequest.isUserAdmin()) ? "ROLE_ADMIN" : "ROLE_USER";
        Role userRole = roleRepository.findByAuthority(role)
                .orElse(roleRepository.save(securityMapper.toRole(role)));

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        return customerRepository.save(securityMapper.toCustomer(customerRequest, authorities));
    }
}

