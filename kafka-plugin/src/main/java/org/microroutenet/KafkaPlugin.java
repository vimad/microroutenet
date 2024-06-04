package org.microroutenet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class KafkaPlugin implements PluginHook {

    private AsyncEventConsumer eventConsumer;
    private Producer producer;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public void startPlugin(String configs) {
        System.out.println("Starting kafka plugin with configs " + configs);
        BaseConfig baseConfig = objectMapper.readValue(configs, BaseConfig.class);
        if (baseConfig instanceof KafkaProducerConfig kp) {
            producer = new Producer(kp);
        }
    }

    @Override
    public String handleRequest(String request) {
        if (producer != null) {
            producer.produce(request);
        }
        return "{\"sent\":\"ok\"}";
    }

    @Override
    public void registerAsyncEventConsumer(AsyncEventConsumer eventConsumer) {
        this.eventConsumer = eventConsumer;
    }
}