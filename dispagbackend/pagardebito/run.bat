echo off
echo "Inicializar Microsservico novo Debito"

##Kafka Config
set KAFKA_SERVER=172.27.85.17:9092
set KAFKA_GROUP=DISPAG
set KAFKATOPIC=NOVODEBITO

##Banco Config
set DATASOURCE_URL=jdbc:postgresql://localhost:5432/tarefas
set DATASOURCE_USERNAME=programador
set DATASOURCE_PASSWORD=hidros

cd target
call java -jar -Xms768M -Xmx768M -Xmn256m -XX:MaxMetaspaceSize=64m -XX:+UseG1GC  novodebito-1.0.0.jar
cd ..
