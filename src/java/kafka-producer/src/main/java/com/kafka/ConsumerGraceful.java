package com.kafka;


//Kafka
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.errors.WakeupException;

// logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// others
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

// main class
public class ConsumerGraceful {

    private static final Logger log = LoggerFactory.getLogger(Consumer.class.getSimpleName());

    public static void main(String[] args) {
        log.info("Consumer Started!");

        String groupId = "java-app";
        String topic = "src-app-java";

        // create Producer Properties
        Properties properties = new Properties();

        // connect to Localhost
       properties.setProperty("bootstrap.servers", "localhost:9092");


        // create consumer configs
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());
        properties.setProperty("group.id", groupId);
        properties.setProperty("auto.offset.reset", "earliest");

        // create a consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        
        // ## ------------------ START GRACEFUL SHUTDOWN STUFF -------------------- ##
        // get a reference to the main thread
        final Thread mainThread = Thread.currentThread();

        // add the shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run(){
                log.info("Detected a shutdown. Exiting by calling consumer.wakeup()..");
                consumer.wakeup();

                // join the main thread to allow th exec out of the code in the main thread
                try {
                    mainThread.join();
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        
        // ## ------------------ END GRACEFUL SHUTDOWN STUFF -------------------- ##

        // adding a try in order to make graceful shutdown works
        try {
            // subscribe to a topic
            consumer.subscribe(Arrays.asList(topic));
            
            // poll for data
            while (true) {

                log.info("Polling");

                ConsumerRecords<String, String> records =
                        consumer.poll(Duration.ofMillis(1000));

                for (ConsumerRecord<String, String> record: records) {
                    log.info("Key: " + record.key() + ", Value: " + record.value());
                    log.info("Partition: " + record.partition() + ", Offset: " + record.offset());
                }
            }
        } catch (WakeupException e) {
            log.info("Consumer is starting to shutdown"); // we use `info` because we already expect the shutdown
        } catch (Exception e){
            log.error("Unexpected exception in the consumer", e);
        } finally {
            consumer.close(); // close the consumer and commit the offsets
            log.info("The consumer is now gracefully shut down!");
        }
    }
}