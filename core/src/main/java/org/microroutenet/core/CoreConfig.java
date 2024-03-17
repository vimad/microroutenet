package org.microroutenet.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "microroutenet-configs")
@Getter
@Setter
public class CoreConfig {
    
    private LocalCommunication localCommunication;
    private List<InterCommunication> interCommunication;
    private Map<String, String> plugins;
    
    @Getter
    @Setter
    public static class LocalCommunication {
        private int port;
    }
    @Getter
    @Setter
    public static class InterCommunication {
        private String name;
        private String plugin;
    }
}
