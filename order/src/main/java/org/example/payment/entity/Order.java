package org.example.payment.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity(name = "customer_order")
public class Order {
    @Id
    private Long id;
    private Long productId;
    private String status;
    private BigDecimal price;
}
