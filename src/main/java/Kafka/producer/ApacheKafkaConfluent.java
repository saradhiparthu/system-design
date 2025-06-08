package Kafka.producer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ConsumerGroupDescription;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DescribeConsumerGroupsOptions;
import org.apache.kafka.clients.admin.DescribeConsumerGroupsResult;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.ListTopicsOptions;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.Node;
import org.apache.kafka.common.TopicPartitionInfo;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

public class ApacheKafkaConfluent {

    public static void main(String[] args) {
        // Configure the AdminClient properties
        Properties adminProps = new Properties();
        // Confluent Cloud bootstrap servers
        adminProps.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, "pkc-921jm.us-east-2.aws.confluent.cloud:9092");
        adminProps.put(AdminClientConfig.SECURITY_PROTOCOL_CONFIG, "SASL_SSL");
        adminProps.put("sasl.jaas.config", "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"BHCTAQMXGP72GLBU\" password=\"N+DdawYGOnvlVcjd9IMoV8C9z2ZWQecmqkPMQAu9p45bVDq19lqEzlApd8ZnBaou\";");
        adminProps.put("sasl.mechanism", "PLAIN");
        adminProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        adminProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        try (AdminClient adminClient = AdminClient.create(adminProps)) {

            // Check if the cluster is available
            Node node = adminClient.describeCluster().controller().get();
            System.out.println(node.host());
            System.out.println(node.idString());
            System.out.println(node.id());
            System.out.println(node.rack());
            System.out.println(node.port());
            System.out.println("Cluster is running.");
            Collection<NewTopic> topics = new ArrayList<>();
            String topicName = "hello";
            short replication_factor = 3;
            int partitions = 3;
			/*
            NewTopic newTopic = new NewTopic(topicName, partitions, replication_factor);
			topics.add(newTopic);
			adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
	        System.out.println("Topic created successfully: " + newTopic.name());
			KafkaProducer<String, String> producer = new KafkaProducer<>(adminProps);
			ProducerRecord<String, String> record = new ProducerRecord<String, String>(newTopic.name(), "Hello!");
			producer.send(record);
			*/
            // List topics
            ListTopicsOptions options = new ListTopicsOptions().listInternal(true); // Exclude internal topics
            ListTopicsResult topicsResult = adminClient.listTopics(options);
            Set<String> topicSet = topicsResult.names().get(); // Print the list of topics
            System.out.println("Number of topics available: " + topics.size());
            for (String topic : topicSet) {
                System.out.println(topic);
            }


            DescribeTopicsResult result = adminClient.describeTopics(Collections.singleton(topicName));
            Map<String, TopicDescription> topicDescriptions = result.allTopicNames().get();
            if (topicDescriptions != null && topicDescriptions.containsKey(topicName)) {
                // Specify the topic for which you want to check
                TopicDescription topicDescription = topicDescriptions.get(topicName);
                System.out.println(
                        "Partitions for topic '" + topicName + "': " + topicDescription.partitions().size());
                for (TopicPartitionInfo partitionInfo : topicDescription.partitions()) {
                    System.out.println("Partition: " + partitionInfo.partition());
                }
            } else {
                System.out.println("Topic '" + topicName + "' not found.");
            }

            adminProps.setProperty("group.id", "test");
            adminProps.setProperty("enable.auto.commit", "true");
            adminProps.setProperty("auto.commit.interval.ms", "1000");
            adminProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            adminProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(adminProps);
            consumer.subscribe(Arrays.asList(topicName));

            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records)
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());

            // Describe consumer groups
            DescribeConsumerGroupsOptions consumergroupsoptions = new DescribeConsumerGroupsOptions();
            DescribeConsumerGroupsResult consumerGroupsResult = adminClient.describeConsumerGroups(Collections.singleton("test"), consumergroupsoptions);
            Map<String, ConsumerGroupDescription> consumerGroups = consumerGroupsResult.all().get();

            // Print the consumer groups
            System.out.println("Consumer Groups:");
            for (Map.Entry<String, ConsumerGroupDescription> entry : consumerGroups.entrySet()) {
                System.out.println("Consumer Group: " + entry.getKey());
                System.out.println("Description: " + entry.getValue());
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
