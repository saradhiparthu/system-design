package Kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class App {
    private static final String TOPIC_NAME = "my-cluster-topic";

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091,localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        while (true) {
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, null, "Counter-" + Math.random());
            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    System.out.println("topic: " + metadata.topic() + " | partition: " + metadata.partition() + " | offset: " + metadata.offset());
                } else {
                    System.out.println("exception: " + exception.getMessage());
                    exception.printStackTrace();
                }
            });
        }
    }
}
