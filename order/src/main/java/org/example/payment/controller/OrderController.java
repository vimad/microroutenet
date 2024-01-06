package org.example.payment.controller;

import org.example.payment.entity.Order;
import org.example.payment.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/order")
public class OrderController {
    
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public Order getById(@PathVariable("id") Long id) {
        return orderService.findById(id);
    }

    @GetMapping("/{id}/{status}")
    public Order updateStatus(@PathVariable("id") Long id, @PathVariable String status) {
        return orderService.updateStatus(id, status);
    }
}
