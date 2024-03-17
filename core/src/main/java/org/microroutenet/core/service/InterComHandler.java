package org.microroutenet.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.microroutenet.core.CoreConfig;
import org.microroutenet.core.RestConfig;
import org.microroutenet.core.model.FromServiceRequestModel;
import org.microroutenet.core.model.ResponseModel;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

import java.util.stream.Collectors;

@Service
public class InterComHandler {

    private final RestClient restClient = RestClient.create();
    private final CoreConfig coreConfig;
    private final RestConfig restConfig;
    private final ObjectMapper objectMapper;

    public InterComHandler(CoreConfig coreConfig, RestConfig restConfig, ObjectMapper objectMapper) {
        this.coreConfig = coreConfig;
        this.restConfig = restConfig;
        this.objectMapper = objectMapper;
    }

    @SneakyThrows
    public ResponseModel handle(HttpServletRequest request) {
        String method = request.getMethod();
        String path = request.getServletPath();
        String responseString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        FromServiceRequestModel fromServiceRequestModel = objectMapper.readValue(responseString, FromServiceRequestModel.class);
        CoreConfig.InterCommunication interCommunication = coreConfig.getInterCommunication()
                .stream().filter(ic -> ic.getName().equals(fromServiceRequestModel.getName()))
                .findFirst()
                .get();
        if ("rest".equals(interCommunication.getPlugin())) {
            return handleRestRequest(fromServiceRequestModel);
        }


        return null;
        
    }

    private ResponseModel handleRestRequest(FromServiceRequestModel fromServiceRequestModel) {
        RestConfig.Configs configs = restConfig.getInfo().stream().filter(info -> info.getName().equals(fromServiceRequestModel.getName()))
                .findFirst().get();
        if (configs.getMethod().equals("GET")) {
            String api = configs.getApi();
            if (StringUtils.hasText(fromServiceRequestModel.getRequestPayload())) {
                String[] split = fromServiceRequestModel.getRequestPayload().split(";");
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

            return ResponseModel.builder()
                    .status(200)
                    .response(response)
                    .build();
        }
        return ResponseModel.builder().build();
    }
}
