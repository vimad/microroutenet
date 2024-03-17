package org.microroutenet.core.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.microroutenet.PluginHook;
import org.microroutenet.core.RestConfig;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

public class RestPlugin implements PluginHook{
    private final RestClient restClient = RestClient.create();
    private RestConfig config;
    
    @SneakyThrows
    @Override
    public void startPlugin(String configs) {
        config = new ObjectMapper().readValue(configs, RestConfig.class);
        System.out.println("Starting rest client");
    }

    @Override
    public String handleRequest(String request) {
        if (config.getMethod().equals("GET")) {
            String api = config.getApi();
            if (StringUtils.hasText(request)) {
                String[] split = request.split(";");
                for (String keyValue : split) {
                    String[] keyValues = keyValue.split("=");
                    String key = keyValues[0];
                    String value = keyValues[1];
                    api = api.replace("{" + key + "}", value);
                }
            }
            String response = restClient.get()
                    .uri(api)
                    .retrieve()
                    .body(String.class);

            return response;
        }
        return "";
    }
}
