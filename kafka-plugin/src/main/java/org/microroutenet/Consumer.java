package org.microroutenet;

import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class Consumer {

    @Setter
    private boolean consume = true;
    private final AsyncEventConsumer eventConsumer;

    @SneakyThrows
    public Consumer(KafkaConsumerConfig kafkaConsumerConfig, AsyncEventConsumer eventConsumer) {
        this.eventConsumer = eventConsumer;
        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConsumerConfig.getBootstrapServers());
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, kafkaConsumerConfig.getGroupId());
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // create the producer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        // subscribe consumer to our topic(s)
        consumer.subscribe(Collections.singletonList(kafkaConsumerConfig.getTopic()));

        Thread.ofPlatform().start(() -> loopConsuming(kafkaConsumerConfig, eventConsumer, consumer));
    }

    private void loopConsuming(KafkaConsumerConfig kafkaConsumerConfig, AsyncEventConsumer eventConsumer, KafkaConsumer<String, String> consumer) {
        while(consume){
            ConsumerRecords<String, String> records =
                    consumer.poll(Duration.ofMillis(100));

            for (ConsumerRecord<String, String> record : records){
                consumeRecord(kafkaConsumerConfig, eventConsumer, record);
            }
        }
    }

    private static void consumeRecord(KafkaConsumerConfig kafkaConsumerConfig, AsyncEventConsumer eventConsumer, ConsumerRecord<String, String> record) {
        String recordValue = record.value();
        System.out.println("consuming ----------" + recordValue);
        AsyncEvent asyncEvent = new AsyncEvent();
        asyncEvent.setMethod(kafkaConsumerConfig.getDestinationMethod());

        String destination = kafkaConsumerConfig.getDestination();
        if (kafkaConsumerConfig.getDestinationMethod().equals("GET")) {
            String[] split = recordValue.split(";");
            for (String keyValue : split) {
                String[] keyValues = keyValue.split("=");
                String key = keyValues[0];
                String value = keyValues[1];
                destination = destination.replace("{" + key + "}", value);
            }
            asyncEvent.setDestination(destination);
            eventConsumer.accept(asyncEvent);
        }
    }

}
