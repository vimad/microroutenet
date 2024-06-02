package org.microroutenet;

public interface PluginHook {
    void startPlugin(String configs);
    String handleRequest(String request);

    default void registerAsyncEventConsumer(AsyncEventConsumer eventConsumer) {}
    default void stopPlugin() {}
}
