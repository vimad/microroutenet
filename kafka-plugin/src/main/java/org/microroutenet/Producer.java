package org.microroutenet;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class Producer {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final KafkaProducer<String, String> producer;
    private final KafkaProducerConfig kafkaProducerConfig;

    @SneakyThrows
    public Producer(String config) {
        kafkaProducerConfig = objectMapper.readValue(config, KafkaProducerConfig.class);

        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProducerConfig.getBootstrapServers());
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create the producer
        producer = new KafkaProducer<>(properties);
    }

    @SneakyThrows
    public void produce(String payload) {
        // create a producer record
        ProducerRecord<String, String> producerRecord =
                new ProducerRecord<>(kafkaProducerConfig.getTopic(), payload);

        producer.send(producerRecord);

        producer.flush();
        producer.close();
    }
}
