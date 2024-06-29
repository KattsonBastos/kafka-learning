#!/bin/bash


build() {

  ## 
  echo "### Maven build"
  mvn clean package
}

pd() { 
  echo "### Producing.."
  java -cp target/kafka-producer-1.0-SNAPSHOT.jar com.kafka.Producer
}

cm() {
  echo "### Consuming.."
  java -cp target/kafka-producer-1.0-SNAPSHOT.jar com.kafka.ConsumerCoop
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