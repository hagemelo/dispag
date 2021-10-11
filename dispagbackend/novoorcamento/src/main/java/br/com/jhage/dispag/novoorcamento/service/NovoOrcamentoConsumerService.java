package br.com.jhage.dispag.novoorcamento.service;

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
import br.com.jhage.dispag.core.modelo.Usuario;
import br.com.jhage.dispag.core.service.DefaultService;
import br.com.jhage.dispag.novoorcamento.exception.LoadOrcamentoException;
import br.com.jhage.dispag.novoorcamento.exception.LoadUsuarioException;
import br.com.jhage.dispag.novoorcamento.repository.OrcamentoRepository;
import br.com.jhage.dispag.novoorcamento.repository.UsuarioRepository;

/**
 * 
 * @author Alexsander Melo
 * @since 11/05/2021
 *
 */

@Service
@Transactional
public class NovoOrcamentoConsumerService extends DefaultService<Orcamento> implements CommandLineRunner{

	private static final Logger logger = LogManager.getLogger(NovoOrcamentoConsumerService.class);
	
	@Autowired
	private OrcamentoRepository orcamentoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	public NovoOrcamentoConsumerService(@Value("${kafka.number.receiver.threads}") Integer numberReceiverThreads) {
		
		super(numberReceiverThreads, Orcamento.class);
	}
	
	@KafkaListener(id = "${kafka.group.id.condif}", topics = "${kafka.topic}")
    public void listen(ConsumerRecord<?, ?> orcamentoConsumerRecord) {
		
		logger.info("Start Consumer NovoOrcamento...");
		try {

			transformToBusinessData(orcamentoConsumerRecord);
			loadUsuario();
			if (orcamentoNaoCadastrado()) {
				orcamentoRepository.save((Orcamento)this.getModelo());
			}
			logger.info("Novo Orcamento Efetuado com <<SUCESSO>>!! ::" + this.getModelo().converterToString());
			logger.info("End Consumer Novoorcamento...");
		}catch (Exception e) {
			StringBuffer buffer  = new StringBuffer()
					.append("Erro ao Cadastrar Novo Orcamento::")
					.append(orcamentoConsumerRecord.toString())
					.append(" | Com Erro::")
					.append(e.getMessage());
			logger.error(buffer.toString());
		}
    }
	
	private void loadUsuario() throws LoadUsuarioException{
		
		Usuario usuario = usuarioRepository.loadUsuarioBy(((Orcamento)this.getModelo()).getUsuarioString()); 
		assert usuario != null : "Usuario Não Encontrado";
		((Orcamento)this.getModelo()).add(usuario);
	}
	
	private boolean orcamentoNaoCadastrado() throws LoadOrcamentoException{
		
		return orcamentoRepository.loadOrcamentoBy(((Orcamento)this.getModelo()).getAno(), ((Orcamento)this.getModelo()).getMes()) ==null;
	}
	
}
