package org.example.payment.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PaymentService {
    
    private final RestClient restClient = RestClient.create();
    public void completePayment(Long id) {
        String orderResult = restClient.get()
                .uri("http://localhost:8081/order/" + id + "/COMPLETED")
                .retrieve()
                .body(String.class);

        String warehouseResult = restClient.get()
                .uri("http://localhost:8082/product/reserve/" + id)
                .retrieve()
                .body(String.class);
        System.out.println(orderResult);
        System.out.println(warehouseResult);
    }
}
