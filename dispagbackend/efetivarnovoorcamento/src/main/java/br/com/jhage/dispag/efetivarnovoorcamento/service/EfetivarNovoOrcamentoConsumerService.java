package br.com.jhage.dispag.efetivarnovoorcamento.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jhage.dispag.core.modelo.Orcamento;
import br.com.jhage.dispag.core.service.DefaultService;
import br.com.jhage.dispag.efetivarnovoorcamento.exception.LoadOrcamentoException;
import br.com.jhage.dispag.efetivarnovoorcamento.repository.OrcamentoRepository;

/**
 * 
 * @author Alexsander Melo
 * @since 12/05/2021
 *
 */

@Service
@Transactional
public class EfetivarNovoOrcamentoConsumerService extends DefaultService<Orcamento> implements CommandLineRunner{

	private static final Logger logger = LogManager.getLogger(EfetivarNovoOrcamentoConsumerService.class);
	
	
	private Orcamento orcamentoLoadedFromdb;
	private final int sleepTime;
	
	
	@Autowired
	private OrcamentoRepository repository;
	
	
	public EfetivarNovoOrcamentoConsumerService(@Value("${kafka.number.receiver.threads}") Integer numberReceiverThreads,
			@Value("${set.sleep.time}") Integer sleepTime) {
		
		super(numberReceiverThreads, Orcamento.class);
		this.sleepTime = sleepTime;
	}
	
	@KafkaListener(id = "${kafka.group.id.condif}", topics = "${kafka.topic}")
    public void listen(ConsumerRecord<?, ?> consumerRecord){
		
		logger.info("Start Consumer Efetivar Novo Orcamento..." );
		try {
			
			Thread.sleep(sleepTime); //Pausa Importante para dar o devido tempo de processamento do NovoOrcamento
			transformToBusinessData(consumerRecord);
			loadOrcamento();
			this.orcamentoLoadedFromdb.aprovar();
			repository.save(this.orcamentoLoadedFromdb);
			logger.info("Orcamento Efetivado com SUCESSO!! ::" + this.orcamentoLoadedFromdb.converterToString());
		}catch (Exception e) {
			e.printStackTrace();
			StringBuffer buffer  = new StringBuffer()
					.append("Erro ao Efetivar Novo Orcamento::")
					.append(consumerRecord.toString())
					.append(" | Com Erro::")
					.append(e.getMessage());
			logger.error(buffer.toString());
		}
    }
	
	private void loadOrcamento() throws LoadOrcamentoException{
		
		this.orcamentoLoadedFromdb = repository.loadOrcamentoBy(((Orcamento)this.getModelo()).getAno(), ((Orcamento)this.getModelo()).getMes()); 
		assert this.orcamentoLoadedFromdb  != null : "Orcamento Nao Encontrado";
	}
	
}
