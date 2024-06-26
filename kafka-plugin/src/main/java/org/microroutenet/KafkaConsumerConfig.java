package org.microroutenet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class KafkaConsumerConfig extends BaseConfig {
    private String destination;
    @JsonProperty("group-id")
    private String groupId;
    @JsonProperty("destination-method")
    private String destinationMethod;
}
