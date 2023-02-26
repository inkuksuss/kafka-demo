package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Producer1 {

    private final static String BOOTSTRAP_SERVER = "localhost:9092";
    private final static String TOPIC_NAME = "topic5";

    public static void main(String args[]) throws ExecutionException, InterruptedException {

        Properties config = new Properties();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        config.put(ProducerConfig.ACKS_CONFIG, "all");
        config.put(ProducerConfig.RETRIES_CONFIG, "100");

        KafkaProducer<String, String> producer = new KafkaProducer<>(config);

        String message = "Third Message";

        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, message);

        RecordMetadata metadata = producer.send(record).get();

        System.out.printf(">>> %s, %d, %d", message, metadata.partition(), metadata.offset());

        producer.flush();
        producer.close();
    }
}
