package br.com.jhage.dispag.efetivarnovodebito.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jhage.dispag.core.modelo.Debitos;
import br.com.jhage.dispag.core.service.DefaultService;
import br.com.jhage.dispag.efetivarnovodebito.exception.LoadDebitosException;
import br.com.jhage.dispag.efetivarnovodebito.repository.DebitosRepository;

/**
 * 
 * @author Alexsander Melo
 * @since 05/02/2021
 *
 */

@Service
@Transactional
public class EfetivarNovoDebitoConsumerService extends DefaultService<Debitos> implements CommandLineRunner{

	private static final Logger logger = LogManager.getLogger(EfetivarNovoDebitoConsumerService.class);
	
	
	private Debitos debitoLoadedFromdb;
	private final int sleepTime;
	
	
	@Autowired
	private DebitosRepository debitosRepository;
	
	
	public EfetivarNovoDebitoConsumerService(@Value("${kafka.number.receiver.threads}") Integer numberReceiverThreads,
			@Value("${set.sleep.time}") Integer sleepTime) {
		
		super(numberReceiverThreads, Debitos.class);
		this.sleepTime = sleepTime;
	}
	
	@KafkaListener(id = "${kafka.group.id.condif}", topics = "${kafka.topic}")
    public void listen(ConsumerRecord<?, ?> consumerRecord){
		
		logger.info("Start Consumer Efetivar Novo Debito..." );
		try {
			Thread.sleep(sleepTime); //Pausa Importante para dar o devido tempo de processamento do NovoDebito
			transformToBusinessData(consumerRecord);
			loadDebito();
			this.debitoLoadedFromdb.aprovar();
			debitosRepository.save(this.debitoLoadedFromdb);
			logger.info("Debito Efetivado com SUCESSO!! ::" + this.debitoLoadedFromdb.converterToString());
		}catch (Exception e) {
			e.printStackTrace();
			StringBuffer buffer  = new StringBuffer()
					.append("Erro ao Efetivar Novo Debito::")
					.append(consumerRecord.toString())
					.append(" | Com Erro::")
					.append(e.getMessage());
			logger.error(buffer.toString());
		}
    }
	
	private void loadDebito() throws LoadDebitosException{
		
		this.debitoLoadedFromdb = debitosRepository.loadCredorByDescricao(((Debitos)this.getModelo()).getMarcacao(), ((Debitos)this.getModelo()).getVencimentoString()); 
		assert this.debitoLoadedFromdb  != null : "Debito Não Encontrado";
	}
	
}
