package br.com.jhage.dispag.cadastrarcredor.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.jhage.dispag.cadastrarcredor.exception.CadastrarCredorConsumerServiceListenException;
import br.com.jhage.dispag.cadastrarcredor.exception.LoadCredorException;
import br.com.jhage.dispag.cadastrarcredor.repository.CredorRepository;
import br.com.jhage.dispag.core.modelo.Credor;
import br.com.jhage.dispag.core.service.DefaultService;

/**
 * 
 * @author Alexsander Melo
 * @since 11/04/2021
 *
 */

@Service
@Transactional
public class CadastrarCredorConsumerService extends DefaultService<Credor> implements CommandLineRunner{

	private static final Logger logger = LogManager.getLogger(CadastrarCredorConsumerService.class);
	
	@Autowired
	private CredorRepository credorRepository;
	
	
	public CadastrarCredorConsumerService(@Value("${kafka.number.receiver.threads}") Integer numberReceiverThreads) {
		
		super(numberReceiverThreads, Credor.class);
	}
	
	@KafkaListener(id = "${kafka.group.id.condif}", topics = "${kafka.topic}")
    public void listen(ConsumerRecord<?, ?> credorConsumerRecord) {
		
		logger.info("Start Consumer Credor ..." );
		try {
			
			transformToBusinessData(credorConsumerRecord);
			if (credorNaoCadastrado()) {
				
				creatCredor();
				logger.info("Credor Efetuado com SUCESSO!! ::" + ((Credor)this.getModelo()).converterToString());
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

	private void creatCredor() throws Exception{
		
		Credor novoCredor = new Credor(((Credor)this.getModelo()).getDescricao(), ((Credor)this.getModelo()).getTipo());
		novoCredor.aprovar();
		credorRepository.save(novoCredor);
	}
	
	private boolean credorNaoCadastrado() throws LoadCredorException{
		
		return credorRepository.loadCredorByDescricao(((Credor)this.getModelo()).getDescricao()) ==null;
	}
	
}
