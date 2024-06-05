from kafka import KafkaProducer
import json
import time

import uuid
import random

# Initialize the Kafka producer
producer = KafkaProducer(
    bootstrap_servers='localhost:9092',
    value_serializer=lambda v: json.dumps(v).encode('utf-8')
)

# Define the topic name
topic_name = 'sales'

# Create a function to send messages
def send_message(producer, topic, message):
    producer.send(topic, message)
    producer.flush()
    print(f"Message sent: {message}")

# Send a few messages
if __name__ == "__main__":
    while True:
        user_id = str(uuid.uuid4())
        message = {'user_id': user_id, 'spending': random.randint(1, 100)}
        send_message(producer, topic_name, message)
        time.sleep(1)  # Adding a small delay between messages
