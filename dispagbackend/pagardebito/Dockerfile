FROM openjdk:14


WORKDIR /usr/src/app  

COPY target/pagardebito-1.0.0.jar ./  

##Kafka Config
ENV KAFKA_SERVER=172.18.197.231:9092
ENV KAFKA_GROUP=DISPAG
ENV KAFKATOPIC=PAGARDEBITO

##Banco Config
ENV DATASOURCE_URL=jdbc:postgresql://192.168.15.51:5432/tarefas
ENV DATASOURCE_USERNAME=programador
ENV DATASOURCE_PASSWORD=hidros

CMD java -jar -Xms768M -Xmx768M -Xmn256m -XX:MaxMetaspaceSize=64m -XX:+UseG1GC  pagardebito-1.0.0.jar