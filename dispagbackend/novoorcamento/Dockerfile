FROM openjdk:14

WORKDIR /usr/src/app  

COPY target/novoorcamento-1.0.0.jar ./  

##Kafka Config
ENV KAFKA_SERVER=172.26.236.117:9092
ENV KAFKA_GROUP=DISPAG
ENV KAFKATOPIC=NOVOORCAMENTO

##Banco Config
ENV DATASOURCE_URL=jdbc:postgresql://192.168.15.51:5432/tarefas
ENV DATASOURCE_USERNAME=programador
ENV DATASOURCE_PASSWORD=hidros

CMD java -jar -XX:+UseG1GC  novoorcamento-1.0.0.jar