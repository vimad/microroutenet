package org.microroutenet.core.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.microroutenet.core.CoreConfig;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/**")
public class CoreController {
    
    private final CoreConfig coreConfig;

    public CoreController(CoreConfig coreConfig) {
        this.coreConfig = coreConfig;
    }

    @RequestMapping
    public ResponseEntity<Object> doGet(HttpServletRequest request) {
        Object response = processRequest(request);
        return ResponseEntity.ok(List.of(coreConfig.getLocalCommunication().getPort()));
    }

    private Object processRequest(HttpServletRequest request) {
        String method = request.getMethod();
        String path = request.getServletPath();
        return null;
    }
}
