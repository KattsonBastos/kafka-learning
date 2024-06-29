#!/bin/bash


mv() {

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
  java -cp target/kafka-producer-1.0-SNAPSHOT.jar com.kafka.Consumer
}


case $1 in
  mv)
    mv
    ;;
  pd)
    pd
    ;;
  cm)
    cm
    ;;
  *)
    echo "Usage: $0 {up, pd, cm}"
    ;;
esac