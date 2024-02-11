package org.microroutenet.core;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "microroutenet-configs")
@Getter
@Setter
public class CoreConfig {
    
    private LocalCommunication localCommunication;
    
    @Getter
    @Setter
    public static class LocalCommunication {
        private int port;
    }
}
