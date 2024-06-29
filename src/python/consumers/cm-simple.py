from kafka import KafkaConsumer
import json
import time
import sys

args = sys.argv

topic_name = args[1]

# Initialize the Kafka consumer
consumer = KafkaConsumer(
    topic_name,
    bootstrap_servers='localhost:9092',
    # auto_offset_reset='earliest',
    # enable_auto_commit=True,
    group_id='sales-group',
)

# Consume messages from the topic
print(f"Consuming messages from the topic {topic_name}...")
for message in consumer:
    print(f"Received message: {message.value}")

