package org.example.warehouse.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "product")
public class Product {
    @Id
    private Long id;
    private String name;
    private int reservedCount;
    private int availableCount;
}
