package org.microroutenet.core;

import org.microroutenet.AsyncEvent;
import org.microroutenet.AsyncEventConsumer;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AsyncEventConsumerImpl implements AsyncEventConsumer {

    private final RestClient restClient = RestClient.create();

    @Override
    public void accept(AsyncEvent event) {
        forwardToRealService(event);
    }

    private void forwardToRealService(AsyncEvent event) {
        String method = event.getMethod();
        String uri = event.getDestination();
        if ("GET".equals(method)) {
            restClient.get()
                    .uri(uri)
                    .retrieve()
                    .body(String.class);
        }
    }
}
