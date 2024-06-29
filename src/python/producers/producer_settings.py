#Create fuction with producer configurations
import json

def producer_settings_json(broker):
    json = {
        'client.id': 'python-app-producer-json',
        'bootstrap.servers': broker,
        'enable.idempotence': "true",
        'acks': "all",
        'linger.ms': 600,
        'batch.size': 50000,
        'compression.type': 'gzip',
        'max.in.flight.requests.per.connection': 5
        }
    return dict(json)


# [avro] = producer config
def producer_settings_avro(broker, schema_registry):

    avro = {
        "client.id": 'series-python-app-producer-avro',
        "bootstrap.servers": broker,
        "schema.registry.url": schema_registry,
        "enable.idempotence": "true",
        "max.in.flight.requests.per.connection": 1,
        "retries": 100,
        "acks": "all",
        "batch.num.messages": 1000,
        "queue.buffering.max.ms": 100,
        "queue.buffering.max.messages": 1000,
        "linger.ms": 1000
        }

    # return data
    return dict(avro)