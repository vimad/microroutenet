package org.microroutenet.core;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class PluginTest {
    
    @Test
    void test() {
        Map<String, String> topic = Map.of("kafka.0.name", "payment-completed-warehouse", "kafka.0.topic", "topic");
        String plugin = "kafka";
        
        Map<String, String> map = new HashMap<>();
        for (Map.Entry<String, String> stringStringEntry : topic.entrySet()) {
            String key = stringStringEntry.getKey();
            String value = stringStringEntry.getValue();
            if (key.startsWith(plugin)) {
                key = key.replaceFirst(plugin + ".\\d+.", "");
                map.put(key, value);
            }
        }
        System.out.println(map);
    }
}
