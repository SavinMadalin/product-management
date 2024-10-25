package com.example.product.controller;

import com.example.product.model.CustomerDetails;
import com.example.product.model.CustomerEntity;
import com.example.product.model.CustomerRequest;
import com.example.product.service.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping("/customers/register")
    public ResponseEntity<CustomerDetails> registerCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.status(201)
                .body(registrationService.registerCustomer(customerRequest));
    }
}
