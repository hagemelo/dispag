package br.com.jhage.dispag.excluirdebito.service;

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
import br.com.jhage.dispag.excluirdebito.exception.ConvertJsonToDebitoException;
import br.com.jhage.dispag.excluirdebito.repository.DebitosRepository;

/**
 * 
 * @author Alexsander Melo
 * @since 05/02/2021
 *
 */

@Service
@Transactional
public class ExcluirDebitoConsumerService implements CommandLineRunner {

	private static final Logger logger = LogManager.getLogger(ExcluirDebitoConsumerService.class);

	private final CountDownLatch latch;
	private String debito;
	private Debitos debitos;

	@Autowired
	private DebitosRepository debitosRepository;

	public ExcluirDebitoConsumerService(@Value("${kafka.number.receiver.threads}") Integer numberReceiverThreads) {

		latch = new CountDownLatch(numberReceiverThreads);
	}

	@KafkaListener(id = "${kafka.group.id.condif}", topics = "${kafka.topic}")
	public void listen(ConsumerRecord<?, ?> debitoConsumerRecord) {

		try {
			logger.info("Consumer value::" + debitoConsumerRecord.toString());
			debito = new String((String) debitoConsumerRecord.value());
			convertJsonToDebito();
			loadDebito();
			debitosRepository.delete(this.debitos);
			logger.info("Debito Pago com SUCESSO!! ::" + this.debitos.converterToString());
			
		} catch (Exception e) {
			e.printStackTrace();
			StringBuffer buffer = new StringBuffer().append("Erro ao receber valor::")
					.append(debitoConsumerRecord.toString()).append(" | Com Erro::").append(e.getMessage());
			logger.error(buffer.toString());
			latch.countDown();
		}
	}

	private void loadDebito() {

		this.debitos = debitosRepository.getOne(this.debitos.getId());
	}

	private void convertJsonToDebito() throws ConvertJsonToDebitoException {

		ObjectMapper mapper = new ObjectMapper();
		try {

			this.debitos = mapper.readValue(this.debito, Debitos.class);
		} catch (IOException e) {

			throw new ConvertJsonToDebitoException(e.getMessage());
		}
	}

	@Override
	public void run(String... args) throws Exception {

		latch.await(300, TimeUnit.DAYS);
	}

}
