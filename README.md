# rest_rabbit_kafka_client_bell_school
Проект который использует api проекта https://github.com/sgoshaa/bellschool.git 

В нем реализовано общение с сервисом через RestTemplate ,RabbitMQ , Kafka

# Для его работы требуется:
# 1. Запустить RabbitMQ, это можно сделать в Docker выполнив команду:
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.10-management
или установить на ПК;
# 2. Запустить Kafka , это можно сделать создав Docker-compose.yml, со следующим содержанием:
https://gist.github.com/sgoshaa/bdc504ee3acdc3682d0837b482633411
# 3. И запустить приложение https://github.com/sgoshaa/bellschool.git

# Или скачать оба приложения и сделать Docker-compose.yml, со следующим содержанием:
https://gist.github.com/sgoshaa/f57271076cfca53b6ca647a79e67c0b9
