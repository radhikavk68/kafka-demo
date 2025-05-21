package org.example.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class SimpleProducer {
    public static void main(String[] args) {
        String topic = "demo-topic";

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            for (int i = 0; i < 10; i++) {
                String value = "Hello Kafka " + i;
                ProducerRecord<String, String> record = new ProducerRecord<>(topic, Integer.toString(i), value);
                producer.send(record, (metadata, exception) -> {
                    if (exception == null) {
                        System.out.println("Sent: " + value + " to " + metadata.topic() + "-" + metadata.partition());
                    } else {
                        exception.printStackTrace();
                    }
                });
            }
        }
    }
}