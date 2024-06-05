

Creating a topic
```
kafka-topics --create --topic=sales --bootstrap-server=localhost:9092 --partitions=3
```

Producing a message
```
kafka-console-producer --bootstrap-server=localhost:9092 --topic=sales
```

Consuming a message
- from beginning: --from-beginning
- setting a group: --group=group_name
```
kafka-console-consumer --bootstrap-server=localhost:9092 --topic=sales 
```

Describing a consumer group
```
kafka-consumer-groups --bootstrap-server=localhost:9092 --group=sales-group --describe
```

Creating a job in flink
```
CREATE TABLE sales (
    user_id STRING,
    spending INT
) WITH (
    'connector' = 'kafka',
    'topic' = 'sales',
    'properties.bootstrap.servers' = 'broker:29092',
    'format' = 'json',
    'scan.startup.mode' = 'earliest-offset'
);

select * from sales;
```


python -m bytewax.run cm-bytewax