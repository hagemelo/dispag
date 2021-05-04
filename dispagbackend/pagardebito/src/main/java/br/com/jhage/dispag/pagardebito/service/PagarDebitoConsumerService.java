package br.com.jhage.dispag.pagardebito.service;

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
import br.com.jhage.dispag.pagardebito.repository.DebitosRepository;

/**
 * 
 * @author Alexsander Melo
 * @since 05/02/2021
 *
 */

@Service
@Transactional
public class PagarDebitoConsumerService extends DefaultService<Debitos> implements CommandLineRunner{

	private static final Logger logger = LogManager.getLogger(PagarDebitoConsumerService.class);

	private Debitos debitoLoadedFromdb;
	
	@Autowired
	private DebitosRepository debitosRepository;
	
	public PagarDebitoConsumerService(@Value("${kafka.number.receiver.threads}") Integer numberReceiverThreads) {
		
		super(numberReceiverThreads, Debitos.class);
	}
	
	@KafkaListener(id = "${kafka.group.id.condif}", topics = "${kafka.topic}")
    public void listen(ConsumerRecord<?, ?> debitoConsumerRecord){
		
		logger.info("Start Consumer Pagar Debito..." );
		try {
			
			transformToBusinessData(debitoConsumerRecord);
			loadDebito();
			this.debitoLoadedFromdb.pagar();
			debitosRepository.save(this.debitoLoadedFromdb);
			logger.info("Debito Pago com SUCESSO!! ::" + this.debitoLoadedFromdb.converterToString());
		}catch (Exception e) {
			e.printStackTrace();
			StringBuffer buffer  = new StringBuffer()
					.append("Erro ao receber valor::")
					.append(debitoConsumerRecord.toString())
					.append(" | Com Erro::")
					.append(e.getMessage());
			logger.error(buffer.toString());
		}
    }
	
	private void loadDebito() {
		
		this.debitoLoadedFromdb = debitosRepository.loadCredorByDescricao(((Debitos)this.getModelo()).getMarcacao(), ((Debitos)this.getModelo()).getVencimentoString()); 
		assert this.debitoLoadedFromdb  != null : "Debito Não Encontrado";
	}

	
}
