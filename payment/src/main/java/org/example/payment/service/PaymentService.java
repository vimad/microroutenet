package org.example.payment.service;

import org.example.payment.model.FromServiceRequestModel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class PaymentService {
    
    private final RestClient restClient = RestClient.create();
    
    
    public void completePayment(Long id) {
        updateOrderService(id);
        updateWarehouseService(id);
    }

    private void updateOrderService(Long id) {
        FromServiceRequestModel orderModel = new FromServiceRequestModel();
        orderModel.setName("payment-completed-order");
        orderModel.setRequestPayload("id="+ id +";status=COMPLETED");
        String orderResult = restClient.post()
                .uri("http://localhost:9093/internal")
                .body(orderModel)
                .retrieve()
                .body(String.class);
        System.out.println(orderResult);
    }

    private void updateWarehouseService(Long id) {
        FromServiceRequestModel warehouseModel = new FromServiceRequestModel();
        warehouseModel.setName("payment-completed-warehouse");
        warehouseModel.setRequestPayload("id="+ id);
        String warehouseResult = restClient.post()
                .uri("http://localhost:9093/internal")
                .body(warehouseModel)
                .retrieve()
                .body(String.class);
        System.out.println(warehouseResult);
    }
}
