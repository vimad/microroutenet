package org.microroutenet;

public interface PluginHook {
    void startPlugin(String configs);
    String handleRequest(String request);
}
