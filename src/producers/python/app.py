# import libs
from producer_json import KafkaProducerJson
from producer_avro import UsersAvro
from schemas import sch_users



if __name__ == '__main__':
    get_dt_rows = 100
    kafka_broker = 'localhost:9092'


    # avro configs
    schema_registry_server = "http://localhost:8081"
    users_avro_topic = 'src-app-users-avro'
    # schema for avro
    schema_key = sch_users.key
    schema_value = sch_users.value

    UsersAvro().avro_producer(kafka_broker, schema_registry_server, schema_key, schema_value, users_avro_topic, get_dt_rows)