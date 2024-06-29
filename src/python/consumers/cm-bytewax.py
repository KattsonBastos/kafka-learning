#python -m bytewax.run cm-bytewax

from bytewax.connectors.kafka import operators as kop, KafkaSinkMessage
from bytewax import operators as op
from bytewax.dataflow import Dataflow

import json

# Kafka broker configuration
brokers = ["localhost:9092"]

# Define the dataflow
flow = Dataflow("filter_high_spending")

# Define the Kafka input
kinp = kop.input("kafka-in", flow, brokers=brokers, topics=["sales"])

# Parse JSON records
def parse_json(record):
    value = record.value
    return json.loads(value)

parsed = op.map("parse_json", kinp.oks, parse_json)

# Filter records where spending is higher than 70
def filter_high_spending(record):
    return record["spending"] > 70

filtered = op.filter("filter_high_spending", parsed, filter_high_spending)

# Serialize records back to JSON
def serialize_json(record):
    return json.dumps(record).encode('utf-8')

serialized = op.map("serialize_json", filtered, serialize_json)

# Define the Kafka output
processed = op.map("map_to_kafka_message", serialized, lambda x: KafkaSinkMessage(None, x))
kop.output("kafka-out", processed, brokers=brokers, topic="high-sales")