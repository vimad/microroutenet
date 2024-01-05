package org.example.warehouse.service;

import jakarta.transaction.Transactional;
import org.example.warehouse.entity.Product;
import org.example.warehouse.repositoy.ProductRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {
    
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }
    
    public Product reserve(Long id) {
        Product product = repository.findById(id).orElseThrow();
        product.setAvailableCount(product.getAvailableCount() - 1);
        product.setReservedCount(product.getReservedCount() + 1); 
        return product;
    }
}
