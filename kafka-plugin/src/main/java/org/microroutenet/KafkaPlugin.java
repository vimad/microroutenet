package org.microroutenet;

public class KafkaPlugin implements PluginHook {

    private AsyncEventConsumer eventConsumer;
    private Producer producer;

    @Override
    public void startPlugin(String configs) {
        System.out.println("Starting kafka plugin with configs " + configs);
        producer = new Producer(configs);
    }

    @Override
    public String handleRequest(String request) {
        producer.produce(request);
        return "{\"sent\":\"ok\"}";
    }

    @Override
    public void registerAsyncEventConsumer(AsyncEventConsumer eventConsumer) {
        this.eventConsumer = eventConsumer;
    }
}