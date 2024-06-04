package org.microroutenet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = KafkaProducerConfig.class, name = "producer"),
        @JsonSubTypes.Type(value = KafkaConsumerConfig.class, name = "consumer")
})
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class BaseConfig {
    @JsonProperty("bootstrap-server")
    private String bootstrapServers;
    private String topic;
    private String type;
}
