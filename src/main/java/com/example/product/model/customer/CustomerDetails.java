package com.example.product.model.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetails {

    private Long id;
    private Set<Role> authorities;
    private String email;
}
