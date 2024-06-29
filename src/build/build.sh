#!/bin/bash

docker compose up -d

docker exec -it broker kafka-topics --create --topic=src-app-java --bootstrap-server=localhost:9092 --partitions=3