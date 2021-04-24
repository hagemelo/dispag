package br.com.jhage.dispag.pagardebito;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.jhage.dispag.pagardebito.service.PagarDebitoConsumerService;

@Component
public class PagarDebitoKafkaConsumerTest extends PagarDebitoConsumerService{

	public PagarDebitoKafkaConsumerTest(@Value("${kafka.number.receiver.threads}") Integer numberReceiverThreads) {
		super(numberReceiverThreads);
	}
	
	
}
