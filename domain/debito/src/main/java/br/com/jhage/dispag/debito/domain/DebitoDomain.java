package br.com.jhage.dispag.debito.domain;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jhage.dispag.core.dto.DebitoDTO;
import br.com.jhage.dispag.core.producer.Pusher;
import br.com.jhage.dispag.core.service.DefaultService;
import br.com.jhage.dispag.core.timer.Timer;
import br.com.jhage.dispag.debito.modelo.Debito;
import br.com.jhage.dispag.debito.repository.DebitoRepository;


/**
 * 
 * @author Alexsander Melo
 * @since 10/09/2021
 *
 */

@Service
@Transactional
public class DebitoDomain extends DefaultService<DebitoDTO, Debito> implements CommandLineRunner{

	private static final Logger logger = LogManager.getLogger(DebitoDomain.class);
	private final DebitoRepository  debitosepository;
	private final Pusher pusher;
	private final String pushVerificarCredorTopic;
	private final Timer timer;
	
	@Autowired
	public DebitoDomain(@Value("${kafka.number.receiver.threads}") Integer numberReceiverThreads,
			DebitoRepository debitosepository, @Qualifier("KAFKA_PRODUCER_PRD") Pusher pusher, @Value("${kafka.producer.verificar.credor.topic}") String pushTopic, Timer timer) {

		super(numberReceiverThreads, DebitoDTO.class);
		this.debitosepository = debitosepository;
		this.pusher = pusher;
		this.pushVerificarCredorTopic = pushTopic;
		this.timer = timer;
	}
	
	//Escuta a Fila do Kafka, papel de consumer, e responde ao tipoco de registrar novo debito
	@KafkaListener(id = "${kafka.group.id.condif}", topics = "${kafka.consumer.registrar.debito.topic}")
	public Boolean resgistrarDebito(ConsumerRecord<?, ?> debitoConsumerRecord){
		
		Boolean result = false;
		logger.info("==> Inicar Registro de Novo Debito.");
		
		this.timer.startTime();
		result |= transformToDTO(debitoConsumerRecord);
		createModelFromDtoObject();
		debitosepository.save((Debito) this.getModel());
		result &= verificarCredorPush();
		logger.info("Registrar Debito, elapsed time " + this.timer.endTime());
		return result;
	}
	
	
	public void aceitarDebito() {
		
		//TODO criar aceitarDebito
	}
	
	public void rejeitarDebito() {
		//TODO criar rejeitarDebito
	}
	
	public void pagarDebito() {
		//TODO criar pagarDebito
	}
	
	private Boolean verificarCredorPush() {
		
		logger.info("==> Iniciar Push para Verificar Credor.");
		this.pusher.push(this.pushVerificarCredorTopic, this.getRecordReceived());
		logger.info("==> Finalizar Push Verificar Credor.");
		return true;
	}
	
	@Override
	public void createModelFromDtoObject() {
		
		DebitoDTO  dto= (DebitoDTO)this.getDtoObject();
		Debito debito = new Debito(dto);
		this.setModel(debito);
	}

}
