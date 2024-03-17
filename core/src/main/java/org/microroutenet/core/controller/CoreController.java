package org.microroutenet.core.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.microroutenet.core.CoreConfig;
import org.microroutenet.core.model.ResponseModel;
import org.microroutenet.core.service.InterComHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
@RequestMapping("/**")
public class CoreController {
    
    private final CoreConfig coreConfig;
    private final InterComHandler interComHandler;
    private final RestClient restClient = RestClient.create();

    public CoreController(CoreConfig coreConfig, InterComHandler interComHandler) {
        this.coreConfig = coreConfig;
        this.interComHandler = interComHandler;
    }

    @RequestMapping
    public ResponseEntity<Object> doGet(HttpServletRequest request) {
        ResponseModel response = processRequest(request);
        return ResponseEntity.status(response.getStatus())
                .body(response.getResponse());
    }

    private ResponseModel processRequest(HttpServletRequest request) {
        String method = request.getMethod();
        String path = request.getServletPath();
        if (path.contains("internal")) {
            return interComHandler.handle(request);
        } else {
            return forwardToRealService(request);
        }
    }

    private ResponseModel forwardToRealService(HttpServletRequest request) {
        String method = request.getMethod();
        String path = request.getServletPath();
        int port = coreConfig.getLocalCommunication().getPort();
        if ("GET".equals(method)) {
            String result = restClient.get()
                    .uri("http://localhost:" + port + path)
                    .retrieve()
                    .body(String.class);
            return ResponseModel.builder()
                    .status(200)
                    .response(result)
                    .build();
        }
        return null;
    }
}
