package org.example.payment.service;

import jakarta.transaction.Transactional;
import org.example.payment.entity.Order;
import org.example.payment.repositoy.OrderRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OrderService {
    
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }
    
    public Order findById(Long id) {
        return repository.findById(id).orElseThrow();
    }

    public Order updateStatus(Long id, String status) {
        Order order = repository.findById(id).orElseThrow();
        order.setStatus(status);
        return repository.save(order);
    }
}
