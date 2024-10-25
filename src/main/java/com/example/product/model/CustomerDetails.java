package com.example.product.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetails {

    private Long id;
    private Set<Role> authorities;
    private String email;
}
