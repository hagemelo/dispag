echo off
echo "Inicializar Microsservico Dominio Debito"

##Kafka Config
set KAFKA_SERVER=172.31.170.90:9092
set KAFKA_GROUP=DISPAG
set KAFKA_CONSUMER_REGISTRAR_DEBITO_TOPIC=REGISTRARDEBITO
set KAFKA_PRODUCER_VERIFICAR_CREDOR_TOPIC=VERIFICARCREDOR

##Banco Config
set DATASOURCE_URL=jdbc:postgresql://localhost:5432/tarefas
set DATASOURCE_USERNAME=programador
set DATASOURCE_PASSWORD=hidros

call java -jar -Xms768M -Xmx768M -Xmn256m -XX:MaxMetaspaceSize=64m -XX:+UseG1GC  target/debito-1.0.0.jar
