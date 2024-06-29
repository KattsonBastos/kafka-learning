#!/bin/bash


build() {

  ## 
  echo "### Maven build"
  mvn clean package
}

pd() { 
  echo "### Producing.."
  java -cp target/kafka-producer-1.0-SNAPSHOT.jar com.kafka.ProducerContinuous
}

cm() {
  echo "### Consuming.."
  java -cp target/kafka-producer-1.0-SNAPSHOT.jar com.kafka.ConsumerGraceful
}


case $1 in
  build)
    build
    ;;
  pd)
    pd
    ;;
  cm)
    cm
    ;;
  *)
    echo "Usage: $0 {build, pd, cm}"
    ;;
esac