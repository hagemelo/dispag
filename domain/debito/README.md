# [DISPAG] MS Dominio Debito


### Serviço

	Components de domínio, responsáveis pela aplicação das regras de negócio e escrita de dados no systema, principal component consumidor dos eventos no Kakfa.

	Principais ações:
		- Resgistrar Debito - Done - GO_TO_TEST
		- Aceitar Debito
		- Rejeitar Debito
		- Pagar Debito
		- Verificar CredorPush


### run
echo off
echo "Inicializar Microsservico Dominio Debito"

##Kafka Config
set KAFKA_SERVER=172.27.85.17:9092
set KAFKA_GROUP=DISPAG
set KAFKA_CONSUMER_REGISTRAR_DEBITO_TOPIC=REGISTRARDEBITO
set KAFKA_PRODUCER_VERIFICAR_CREDOR_TOPIC=VERIFICARCREDOR


##Banco Config
set DATASOURCE_URL=jdbc:postgresql://localhost:5432/tarefas
set DATASOURCE_USERNAME=user
set DATASOURCE_PASSWORD=user

cd target
call java -jar -Xms1G -Xmx1G -Xmn256m -XX:MaxMetaspaceSize=128m -XX:+UseG1GC  debito-1.0.0.jar
cd ..




