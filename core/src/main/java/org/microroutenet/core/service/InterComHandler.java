package org.microroutenet.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.microroutenet.AsyncEventConsumer;
import org.microroutenet.core.CoreConfig;
import org.microroutenet.core.model.FromServiceRequestModel;
import org.microroutenet.core.model.ResponseModel;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.microroutenet.PluginHook;

@Service
public class InterComHandler {

    
    private final CoreConfig coreConfig;
    private final ObjectMapper objectMapper;
    private final AsyncEventConsumer asyncEventConsumer;
    
    private final Map<String, PluginHook> hooks = new HashMap<>();

    public InterComHandler(CoreConfig coreConfig, ObjectMapper objectMapper, AsyncEventConsumer asyncEventConsumer) {
        this.coreConfig = coreConfig;
        this.objectMapper = objectMapper;
        this.asyncEventConsumer = asyncEventConsumer;
        initializeListeners();
    }

    private void initializeListeners() {
        if (coreConfig.getInterCommunication() == null) {
            return;
        }
        for (CoreConfig.InterCommunication interCommunication : coreConfig.getInterCommunication()) {
            if ("consumer".equals(interCommunication.getType())) {
                startAndGetPluginHook(interCommunication);
            }
        }
    }

    @SneakyThrows
    public ResponseModel handle(HttpServletRequest request) {
        String responseString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        FromServiceRequestModel fromServiceRequestModel = objectMapper.readValue(responseString, FromServiceRequestModel.class);
        CoreConfig.InterCommunication interCommunication = coreConfig.getInterCommunication()
                .stream().filter(ic -> ic.getName().equals(fromServiceRequestModel.getName()))
                .findFirst()
                .get();
        return handleFromPlugin(interCommunication, fromServiceRequestModel);
    }

    @SneakyThrows
    private ResponseModel handleFromPlugin(CoreConfig.InterCommunication interCommunication, FromServiceRequestModel fromServiceRequestModel) {
        PluginHook pluginHook = startAndGetPluginHook(interCommunication);
        hooks.put(interCommunication.getName(), pluginHook);
        String response = pluginHook.handleRequest(fromServiceRequestModel.getRequestPayload());
        return ResponseModel.builder()
                .status(200)
                .response(response)
                .build();
    }

    @SneakyThrows
    private PluginHook startAndGetPluginHook(CoreConfig.InterCommunication interCommunication) {
        String configForPlugin = getConfigForPlugin(interCommunication.getName(), interCommunication.getPlugin());
        String className = coreConfig.getPlugins().get(interCommunication.getPlugin() + "-class-name");
//        PluginHook pluginHook = hooks.get(interCommunication.getName());
        Class<?> aClass = Class.forName(className);
        PluginHook pluginHook = (PluginHook)aClass.getConstructor().newInstance();
        pluginHook.startPlugin(configForPlugin);
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
