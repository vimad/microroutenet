package org.microroutenet.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "microroutenet-configs")
@Getter
@Setter
public class CoreConfig {
    
    private LocalCommunication localCommunication;
    private List<InterCommunication> interCommunication;
    
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
