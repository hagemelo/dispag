package br.com.jhage.dispag.excluirdebito;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.jhage.dispag.excluirdebito.service.ExcluirDebitoConsumerService;

@Component
public class ExcluirDebitoKafkaConsumerTest extends ExcluirDebitoConsumerService{

	public ExcluirDebitoKafkaConsumerTest(@Value("${kafka.number.receiver.threads}") Integer numberReceiverThreads) {
		super(numberReceiverThreads);
	}
	
	
}
