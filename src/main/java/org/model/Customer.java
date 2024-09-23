package org.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Customer {
    @Id
    private String id; // Unique customer ID
    private String password; // For simplicity, use plain text; in production, hash it

}