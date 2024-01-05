package org.example.warehouse.controller;

import org.example.warehouse.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/payment")
public class PaymentController {
    
    private final PaymentService paymentService;

    public PaymentController(PaymentService orderService) {
        this.paymentService = orderService;
    }

    @GetMapping("/{id}")
    public void pay(@PathVariable("id") Long id) {
        paymentService.completePayment();
    }
}
