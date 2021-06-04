echo off
echo "Inicializar Microsservico Efetivar Novo Debito"

##Kafka Config
set KAFKA_SERVER=172.24.158.86:9092
set KAFKA_GROUP=DISPAG
set KAFKATOPIC=EFETIVARNOVODEBITO

##Banco Config
set DATASOURCE_URL=jdbc:postgresql://localhost:5432/tarefas
set DATASOURCE_USERNAME=programador
set DATASOURCE_PASSWORD=hidros

cd target
call java -jar -Xms768M -Xmx768M -Xmn256m -XX:MaxMetaspaceSize=64m -XX:+UseG1GC  efetivarnovodebito-1.0.0.jar
cd ..
