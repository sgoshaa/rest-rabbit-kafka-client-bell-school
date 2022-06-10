# rest_rabbit_kafka_client_bell_school
Проект который использует api проекта https://github.com/sgoshaa/bellschool.git 

В нем реализовано общение с сервисом через RestTemplate ,RabbitMQ , Kafka

# Для его работы требуется:
# 1.запустить RabbitMQ, это можно сделать в Docker выполнив команду:
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.10-management
или установить на ПК;
# 2.Запустить Kafka , это можно сделать создав Docker-compose.yml, со следующим содержанием:
version: "2"

services:
  zookeeper:
    image: docker.io/bitnami/zookeeper:3.8
    ports:
      - "2181:2181"
    volumes:
      - "zookeeper_data:/bitnami"
    environment:
      - ALLOW_ANONYMOUS_LOGIN=yes
  kafka:
    image: docker.io/bitnami/kafka:3.2
    ports:
      - "9092:9092"
    volumes:
      - "kafka_data:/bitnami"
    environment:
      - KAFKA_CFG_ZOOKEEPER_CONNECT=zookeeper:2181
      - ALLOW_PLAINTEXT_LISTENER=yes
      - KAFKA_CFG_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092
    depends_on:
      - zookeeper

volumes:
  zookeeper_data:
    driver: local
  kafka_data:
    driver: local
# 3.И заупустить приложение https://github.com/sgoshaa/bellschool.git 
