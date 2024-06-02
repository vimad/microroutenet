package org.microroutenet.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.microroutenet.AsyncEventConsumer;
import org.microroutenet.PluginHook;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InitializeListeners {

    private final AsyncEventConsumer asyncEventConsumer;
    private final CoreConfig coreConfig;
    private final ObjectMapper objectMapper;

    public InitializeListeners(AsyncEventConsumer asyncEventConsumer, CoreConfig coreConfig, ObjectMapper objectMapper) {
        this.asyncEventConsumer = asyncEventConsumer;
        this.coreConfig = coreConfig;
        this.objectMapper = objectMapper;
        initializeListeners();
    }

    public void initializeListeners() {
        if (coreConfig.getInterCommunication() == null) {
            return;
        }
        for (CoreConfig.InterCommunication interCommunication : coreConfig.getInterCommunication()) {
            if ("consumer".equals(interCommunication.getType())) {
                startAndGetPluginHook(interCommunication);
            }
        }
    }

    public void stopAllPlugins() {
        if (coreConfig.getInterCommunication() == null) {
            return;
        }
        for (CoreConfig.InterCommunication interCommunication : coreConfig.getInterCommunication()) {
            PluginHook pluginHook = getPluginHook(interCommunication);
            pluginHook.stopPlugin();
        }
    }

    @SneakyThrows
    private PluginHook startAndGetPluginHook(CoreConfig.InterCommunication interCommunication) {
        String configForPlugin = getConfigForPlugin(interCommunication.getName(), interCommunication.getPlugin());
        PluginHook pluginHook = getPluginHook(interCommunication);
        pluginHook.startPlugin(configForPlugin);
        return pluginHook;
    }

    @SneakyThrows
    private PluginHook getPluginHook(CoreConfig.InterCommunication interCommunication) {
        String className = coreConfig.getPlugins().get(interCommunication.getPlugin() + "-class-name");
//        PluginHook pluginHook = hooks.get(interCommunication.getName());
        Class<?> aClass = Class.forName(className);
        PluginHook pluginHook = (PluginHook)aClass.getConstructor().newInstance();
        return pluginHook;
    }

    @SneakyThrows
    private String getConfigForPlugin(String name, String plugin) {
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, String> stringStringEntry : coreConfig.getPlugins().entrySet()) {
            String key = stringStringEntry.getKey();
            String value = stringStringEntry.getValue();
            if (key.startsWith(plugin)) {
                key = key.replaceFirst(plugin + "-" + name + ".", "");
                map.put(key, value);
            }
        }
        return objectMapper.writeValueAsString(map);
    }
}
