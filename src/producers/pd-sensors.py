import sys
import pandas as pd

import concurrent.futures
import json
import math
from datetime import datetime, timedelta, timezone
from random import random
from time import sleep

from confluent_kafka import Producer
from confluent_kafka.admin import AdminClient, NewTopic
def create_local_kafka_producer(topic_name, servers=None):
    servers = servers if servers is not None else ["localhost:9092"]
    config = {
        "bootstrap.servers": ",".join(servers),
    }

    # Create Kafka topic
    admin = AdminClient(config)
    topic = NewTopic(topic_name, num_partitions=3)
    for future in admin.create_topics([topic]).values():
        concurrent.futures.wait([future])

    return Producer(config)


def create_temperature_events(topic_name, servers=None):
    producer = create_local_kafka_producer(topic_name, servers)
    for i in range(10):
        dt = datetime.now(timezone.utc)
        if random() > 0.8:
            dt = dt - timedelta(minutes=random())
        event = {"type": "temp", "value": i, "time": dt.isoformat()}
        producer.produce(topic_name, json.dumps(event).encode("utf-8"))
        # producer.flush()
        print(f"event sent: {event}")
        sleep(1)

    producer.flush()


if __name__ == '__main__':
    args = sys.argv

    create_temperature_events(args[1], ['localhost:9092'])