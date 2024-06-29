package com.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Properties;

import java.util.UUID;
import com.github.javafaker.Faker;
import java.util.Locale;

public class App {
    public static void main(String[] args) {
        String topic = "users";

        // Faker faker = new Faker();

        Faker faker = new Faker(new Locale("en-US"));

        

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        try {
            for (int i = 0; i < 10; i++){
                String userName = faker.name().username();
                String uuid = UUID.randomUUID().toString();
                String cellPhone = faker.phoneNumber().cellPhone();               
                

                User user = new User(userName, uuid, cellPhone);
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonValue = objectMapper.writeValueAsString(user);

                ProducerRecord<String, String> record = new ProducerRecord<>(topic, uuid, jsonValue);
                producer.send(record);
                System.out.println("Message sent successfully: " + jsonValue);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}
