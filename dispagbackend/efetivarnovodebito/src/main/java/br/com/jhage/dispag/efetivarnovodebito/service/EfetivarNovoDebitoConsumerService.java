package br.com.jhage.dispag.efetivarnovodebito.service;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jhage.dispag.core.modelo.Debitos;
import br.com.jhage.dispag.efetivarnovodebito.exception.ConvertJsonToNovoDebitoException;
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
public class EfetivarNovoDebitoConsumerService implements CommandLineRunner{

	private static final Logger logger = LogManager.getLogger(EfetivarNovoDebitoConsumerService.class);
	
	private final CountDownLatch latch;
	
	private ConsumerRecord<?, ?> debitoConsumerReceived;
	private Debitos debitosKafkaReceived;
	private Debitos debitoLoadedFromdb;
	private final int sleepTime;
	
	
	@Autowired
	private DebitosRepository debitosRepository;
	
	
	public EfetivarNovoDebitoConsumerService(@Value("${kafka.number.receiver.threads}") Integer numberReceiverThreads,
			@Value("${set.sleep.time}") Integer sleepTime) {
		
		latch = new CountDownLatch(numberReceiverThreads);
		this.sleepTime = sleepTime;
	}
	
	@KafkaListener(id = "${kafka.group.id.condif}", topics = "${kafka.topic}")
    public void listen(ConsumerRecord<?, ?> consumerRecord){
		
		try {
			logger.info("Consumer value::" + consumerRecord.toString());
			Thread.sleep(sleepTime); //Pausa Importante para dar o devido tempo de processamento do NovoDebito
			debitoConsumerReceived = consumerRecord;
			convertJsonToNovoDebito();
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
			latch.countDown();
		}
    }
	
	private void loadDebito() throws LoadDebitosException{
		
		this.debitoLoadedFromdb = debitosRepository.loadCredorByDescricao(this.debitosKafkaReceived.getMarcacao(), this.debitosKafkaReceived.getVencimentoString()); 
				
		assert this.debitoLoadedFromdb  != null : "Orcamento Não Encontrado";
	}
	
	private void convertJsonToNovoDebito() throws ConvertJsonToNovoDebitoException{
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			this.debitosKafkaReceived = mapper.readValue(new String((String) debitoConsumerReceived.value()), Debitos.class);
		} catch (IOException e) {
			
			throw new ConvertJsonToNovoDebitoException(e.getMessage());
		}  
	}
	
	
	@Override
	public void run(String... args) throws Exception {
		
		latch.await(300, TimeUnit.DAYS);
	}
	
}
