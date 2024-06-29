package com.kafka;

import java.util.Properties;

// logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Kafka
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.common.serialization.StringSerializer;

public class Producer {

    // # -- creating a logger
    private static final Logger log = LoggerFactory.getLogger(Producer.class.getSimpleName());

    public static void main(String[] args){
        log.info("Started Kafka Producer!");

        String topic = "src-app-java";

        // # -- create Producer Properties - Properties is basically the config of our producer
        Properties properties = new Properties();

        // setting the broker properties
        properties.setProperty("bootstrap.servers", "localhost:9092");

        // setting the producer specific properties - both props below serialize the keys and values using an official Kafka class
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        // # -- create the Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        for (int j=0; j<2; j++){
            for (int i=0; i < 10; i++){

                String key = "id_" + i;
                String value = "Msg " + i;
                // # -- create a Producer Record
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, value); // if we don't want keys, just pass (topic, value)

                // # -- send data
                producer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception e) {
                        // executed every time a record successfully sent or exception is thrown
                        if (e == null) {
                            // record successfully sent
                            log.info(
                                "Key: " + key + " | " +
                                "Topic: " + metadata.topic() + " | " +
                                "Partition: " + metadata.partition() + " | " +
                                "Offset: " + metadata.offset() + " | " +
                                "Timestamp: " + metadata.timestamp() + " | "
                            );
                        } else {
                            log.error("Error while producing: ", e);
                        }
                    }
                });

            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        

        // # -- flush and close the producer
        producer.flush(); // tell the producer to send all data and block until done - synchronous
        producer.close();
    }

}
