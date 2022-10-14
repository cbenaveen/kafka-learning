package org.cbenaveen.kafka.java.examples;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaConsumerExample {
    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerExample.class);

    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "simple-consumer");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        KafkaConsumer<String, String> consumer = new KafkaConsumer(properties);
        consumer.subscribe(Collections.singleton("python-sample"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(2));

            if (records.isEmpty()) continue;

            LOGGER.info("Total # of messages received are {}", records.count());
            records.iterator().forEachRemaining(record -> {
                LOGGER.info(String.format("Record received: Partition %s, offset: %s, key: %s, value: %s",
                        record.partition(), record.offset(), record.key(), record.value()));
            });
        }
    }
}
