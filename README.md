# DISPAG

### Projeto
Projeto de controle financeiro pessoal de contas a pagar.

O objetivo é apenas reunir conhecimento de tecnologias. 

==> Projeto Ainda em Construção


### Tecnologias

- Serverless Framework
- NodeJS
- Springboot
- AWS
- Docker
- Apache Kafka
- Flutter


### Arquitetura da Solução
![N|Solid](https://github.com/hagemelo/dispag/blob/main/ArquiteturadaSolucao.JPG)

	A solução de arquitetura utilizada neste projeto, é o CQRS, corresponde me inglês, Command Query Responsibility Segregation.
	O porpósito de uso é desenvolver um exemplo de solução de sistemas reativos, onde o tempo de tratamento das requisições devem atender a rezoabilidade, mantendo-se responsivo sob várias cargas, com ajuste na elasticidade e também na ocorrências de falhas, pois a solução projetada de modo a tratar as falhas, e por fim utilizando troca de mensagens assícronas entre os componentes. 


	###### Client Applications
		Camada cliente, onde as interfaces de acesso à aplicação se por meio do App ou Web view.


	###### Presentation
		Conteúdo estático, criado principalmente em html


	###### API Management
		Serviço utilizado da AWS API Gateway, por onde será exposto as APIs Rest das Stacks Command e Query.

	###### Command Stack
		Stack responsável pelas ações de escrita de dados

		####### Application
			APIs Rest reponsáveis por expor as principais ações de escrita de dados para a toda a solução. 


		####### Event Sourcing
			Serviço com responsabilidade de persistir as transações, importante nas ações de resiliência em eventos de falha. a Tecnologia adotada até aqui é o Kakfa

		####### Domain
			Components de domínio, responsáveis pela aplicação das regras de negócio e escrita de dados no systema, principal component consumidor dos eventos no Kakfa.

	###### Query Stack
		Stack responsável pelas ações de leitura de dados
	

		####### Application
			APIs Rest reponsáveis por expor as principais ações de leitura de dados para a toda a solução. 


0