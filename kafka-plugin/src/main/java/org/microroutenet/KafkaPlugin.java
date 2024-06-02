package org.microroutenet;

public class KafkaPlugin implements PluginHook {

    private AsyncEventConsumer eventConsumer;

    @Override
    public void startPlugin(String configs) {
        System.out.println("Starting kafka plugin with configs " + configs);
    }

    @Override
    public String handleRequest(String request) {
        return "{\"id\":1,\"name\":\"phone\",\"reservedCount\":69,\"availableCount\":9931}";
    }

    @Override
    public void registerAsyncEventConsumer(AsyncEventConsumer eventConsumer) {
        this.eventConsumer = eventConsumer;
    }
}