package br.com.jhage.dispag.novodebito.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jhage.dispag.core.constante.Mes;
import br.com.jhage.dispag.core.modelo.Credor;
import br.com.jhage.dispag.core.modelo.Debitos;
import br.com.jhage.dispag.core.modelo.Orcamento;
import br.com.jhage.dispag.core.service.DefaultService;
import br.com.jhage.dispag.novodebito.exception.LoadCredorException;
import br.com.jhage.dispag.novodebito.exception.LoadOrcamentoException;
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
public class NovoDebitoConsumerService extends DefaultService<Debitos> implements CommandLineRunner{

	private static final Logger logger = LogManager.getLogger(NovoDebitoConsumerService.class);
	
	@Autowired
	private DebitosRepository debitosRepository;
	
	@Autowired
	private CredorRepository credorRepository;
	
	@Autowired
	private OrcamentoRepository orcamentoRepository;
	
	
	public NovoDebitoConsumerService(@Value("${kafka.number.receiver.threads}") Integer numberReceiverThreads) {
		
		super(numberReceiverThreads, Debitos.class);
	}
	
	@KafkaListener(id = "${kafka.group.id.condif}", topics = "${kafka.topic}")
    public void listen(ConsumerRecord<?, ?> debitoConsumerRecord) {
		
		
		logger.info("Start Consumer Novodebito...");
		try {
			transformToBusinessData(debitoConsumerRecord);
			loadCredor();
			loadOrcamento();
			debitosRepository.save((Debitos)this.getModelo());
			logger.info("Novo Debito Efetuado com <<SUCESSO>>!! ::" + this.getModelo().converterToString());
			logger.info("End Consumer Novodebito...");
		}catch (Exception e) {
			StringBuffer buffer  = new StringBuffer()
					.append("Erro ao receber valor::")
					.append(debitoConsumerRecord.toString())
					.append(" | Com Erro::")
					.append(e.getMessage());
			logger.error(buffer.toString());
		}
    }
	
	private void loadCredor() throws LoadCredorException{
		
		Credor credor = credorRepository.loadCredorByDescricao(((Debitos)this.getModelo()).getCredor().getDescricao());
		assert credor != null : "Credor Não Encontrado";
		((Debitos)this.getModelo()).add(credor);
	}
	
	private void loadOrcamento() throws LoadOrcamentoException{
		
		int ano = ((Debitos)this.getModelo()).getOrcamento().getAno();
		Mes mes =  ((Debitos)this.getModelo()).getOrcamento().getMes();
		Orcamento orc = orcamentoRepository.loadOrcamentoBy(ano, mes);
		assert orc != null : "Orcamento Não Encontrado";
		((Debitos)this.getModelo()).add(orc);
	}
	
}
