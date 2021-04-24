package br.com.jhage.dispag.cadastrarcredor.service;

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

import br.com.jhage.dispag.cadastrarcredor.exception.CadastrarCredorConsumerServiceListenException;
import br.com.jhage.dispag.cadastrarcredor.exception.ConvertJsonToCadastrarCredorException;
import br.com.jhage.dispag.cadastrarcredor.exception.LoadCredorException;
import br.com.jhage.dispag.cadastrarcredor.repository.CredorRepository;
import br.com.jhage.dispag.core.modelo.Credor;

/**
 * 
 * @author Alexsander Melo
 * @since 11/04/2021
 *
 */

@Service
@Transactional
public class CadastrarCredorConsumerService implements CommandLineRunner{

	private static final Logger logger = LogManager.getLogger(CadastrarCredorConsumerService.class);
	
	private final CountDownLatch latch;
	private String cadastrarcredor;
	private Credor credor,novoCredor;
	
	
	@Autowired
	private CredorRepository credorRepository;
	
	
	public CadastrarCredorConsumerService(@Value("${kafka.number.receiver.threads}") Integer numberReceiverThreads) {
		
		latch = new CountDownLatch(numberReceiverThreads);
	}
	
	@KafkaListener(id = "${kafka.group.id.condif}", topics = "${kafka.topic}")
    public void listen(ConsumerRecord<?, ?> credorConsumerRecord) {
		
		try {
			logger.info("Consumer value::" + credorConsumerRecord.toString());
			cadastrarcredor = new String((String) credorConsumerRecord.value());
			convertJsonToCadastrarCredor();
			loadCredor();
			if (this.credor == null) {
				credorRepository.save(new Credor(this.novoCredor.getDescricao(), this.novoCredor.getTipo()));
				logger.info("Credor Efetuado com SUCESSO!! ::" + this.novoCredor.converterToString());
			}
		}catch (Exception e) {
			StringBuffer buffer  = new StringBuffer()
					.append("Erro Cadastrar Credor::")
					.append(credorConsumerRecord.toString())
					.append(" | Com Erro::")
					.append(e.getMessage());
			logger.error(buffer.toString());
			throw new CadastrarCredorConsumerServiceListenException();
		}
    }
	
	private void loadCredor() throws LoadCredorException{
		
		this.credor = credorRepository.loadCredorByDescricao(this.novoCredor.getDescricao());
	}
	
	private void convertJsonToCadastrarCredor() throws ConvertJsonToCadastrarCredorException{
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			this.novoCredor = mapper.readValue(this.cadastrarcredor, Credor.class);
		} catch (IOException e) {
			
			throw new ConvertJsonToCadastrarCredorException(e.getMessage());
		}  
	}
	
	public CountDownLatch getLatch() {
		return latch;
	}

	@Override
	public void run(String... args) throws Exception {
		
		latch.await(600, TimeUnit.SECONDS);
	}
	
}
