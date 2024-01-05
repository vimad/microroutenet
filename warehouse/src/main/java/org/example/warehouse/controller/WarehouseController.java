package org.example.warehouse.controller;

import org.example.warehouse.entity.Product;
import org.example.warehouse.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/product")
public class WarehouseController {
    
    private final ProductService productService;

    public WarehouseController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Product reserve(@PathVariable("id") Long id) {
        return productService.reserve(id);
    }
}
