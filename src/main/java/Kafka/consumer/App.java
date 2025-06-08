package Kafka.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class App {
    private static final String TOPIC_NAME = "my-cluster-topic";

    public static void main(String[] args) {
        Properties props = new Properties();
        // Kafka broker addresses
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091,localhost:9092");

        // Consumer Group ID (ensures multiple consumers in the same group balance the load)
        //props.put(ConsumerConfig.GROUP_ID_CONFIG, "my-consumer-group");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "new-consumer-group-1");

        // Disable auto-commit for better control over offset commits
        //props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        // Deserializers (for String keys and values)
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // Subscribe to the topic
        consumer.subscribe(Arrays.asList(TOPIC_NAME));

        // Graceful shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Closing consumer...");
            consumer.close();
        }));

        try {
            consumer.assignment().forEach(partition -> System.out.println("Assigned Partition: " + partition));
            while (true) {
                // Poll for new records
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                System.out.println("Records empty" + records.isEmpty());
                for (ConsumerRecord<String, String> record : records) {
                    System.out.printf("offset = %d, key = %s, value = %s%n",
                            record.offset(), record.key(), record.value());
                }

                // Manually commit offsets after processing the records
                //consumer.commitSync();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close(); // Ensure consumer closes properly
        }
    }
}
