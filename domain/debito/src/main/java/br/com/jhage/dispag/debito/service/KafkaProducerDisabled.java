package br.com.jhage.dispag.debito.service;

import org.springframework.stereotype.Component;

/**
 * 
 * @author Alexsander Melo
 * @since 10/10/2021
 *
 */
@Component("KAFKA_PRODUCER_DISABLED")
public class KafkaProducerDisabled implements PusherService{

	@Override
	public Boolean push(String topic, String payload) {
		
		System.out.println("==> Execução do Push está desabilitado.");// TODO Auto-generated method stub
		return null;
	}

}
