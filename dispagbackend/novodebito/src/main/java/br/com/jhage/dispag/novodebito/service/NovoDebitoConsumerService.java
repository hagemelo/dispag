package br.com.jhage.dispag.novodebito.service;

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

import br.com.jhage.dispag.core.constante.Mes;
import br.com.jhage.dispag.core.modelo.Credor;
import br.com.jhage.dispag.core.modelo.Debitos;
import br.com.jhage.dispag.core.modelo.Orcamento;
import br.com.jhage.dispag.novodebito.exception.ConvertJsonToNovoDebitoException;
import br.com.jhage.dispag.novodebito.exception.LoadCredorException;
import br.com.jhage.dispag.novodebito.exception.LoadOrcamentoException;
import br.com.jhage.dispag.novodebito.exception.NovoDebitoConsumerServiceListenException;
import br.com.jhage.dispag.novodebito.repository.CredorRepository;
import br.com.jhage.dispag.novodebito.repository.DebitosRepository;
import br.com.jhage.dispag.novodebito.repository.OrcamentoRepository;

/**
 * 
 * @author Alexsander Melo
 * @since 05/02/2021
 *
 */

@Service
@Transactional
public class NovoDebitoConsumerService implements CommandLineRunner{

	private static final Logger logger = LogManager.getLogger(NovoDebitoConsumerService.class);
	
	private final CountDownLatch latch;
	private String novodebito;
	private Debitos debitos;
	
	@Autowired
	private DebitosRepository debitosRepository;
	
	@Autowired
	private CredorRepository credorRepository;
	
	@Autowired
	private OrcamentoRepository orcamentoRepository;
	
	
	public NovoDebitoConsumerService(@Value("${kafka.number.receiver.threads}") Integer numberReceiverThreads) {
		
		latch = new CountDownLatch(numberReceiverThreads);
	}
	
	@KafkaListener(id = "${kafka.group.id.condif}", topics = "${kafka.topic}")
    public void listen(ConsumerRecord<?, ?> debitoConsumerRecord) {
		
		try {
			logger.info("Consumer value::" + debitoConsumerRecord.toString());
			novodebito = new String((String) debitoConsumerRecord.value());
			convertJsonToNovoDebito();
			loadCredor();
			loadOrcamento();
			debitosRepository.save(this.debitos);
			logger.info("Debito Efetuado com SUCESSO!! ::" + this.debitos.converterToString());
			
		}catch (Exception e) {
			StringBuffer buffer  = new StringBuffer()
					.append("Erro ao receber valor::")
					.append(debitoConsumerRecord.toString())
					.append(" | Com Erro::")
					.append(e.getMessage());
			logger.error(buffer.toString());
			throw new NovoDebitoConsumerServiceListenException();
		}
    }
	
	private void loadCredor() throws LoadCredorException{
		
		Credor credor = credorRepository.loadCredorByDescricao(this.debitos.getCredor().getDescricao());
		assert credor != null : "Credor Não Encontrado";
		this.debitos.add(credor);
	}
	
	private void loadOrcamento() throws LoadOrcamentoException{
		
		int ano = this.debitos.getOrcamento().getAno();
		Mes mes =  this.debitos.getOrcamento().getMes();
		Orcamento orc = orcamentoRepository.loadOrcamentoBy(ano, mes);
		assert orc != null : "Orcamento Não Encontrado";
		this.debitos.add(orc);
	}
	
	private void convertJsonToNovoDebito() throws ConvertJsonToNovoDebitoException{
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			this.debitos = mapper.readValue(this.novodebito, Debitos.class);
		} catch (IOException e) {
			
			throw new ConvertJsonToNovoDebitoException(e.getMessage());
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
