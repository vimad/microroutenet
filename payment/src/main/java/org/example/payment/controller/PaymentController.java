package org.example.payment.controller;

import org.example.payment.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping(path = "/payment")
public class PaymentController {
    
    private final PaymentService paymentService;

    public PaymentController(PaymentService orderService) {
        this.paymentService = orderService;
    }

    @GetMapping("/{id}")
    public void pay(@PathVariable("id") Long id) {
        paymentService.completePayment(id);
    }
}
