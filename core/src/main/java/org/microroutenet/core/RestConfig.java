package org.microroutenet.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "microroutenet-configs.rest")
@Getter
@Setter
public class RestConfig {
    
    private List<Configs> info;
    
    @Setter
    @Getter
    public static class Configs {
        private String name;
        private String api;
        private String method;
    }
}
