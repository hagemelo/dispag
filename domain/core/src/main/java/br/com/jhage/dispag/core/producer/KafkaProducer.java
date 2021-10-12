package br.com.jhage.dispag.core.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


/**
 * 
 * @author Alexsander Melo
 * @since 10/10/2021
 *
 */
@Component
public class KafkaProducer implements Pusher{

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class); 
	
	
    private private KafkaTemplate<String, String> kafkaTemplate;
	
    @Autowired
	public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
		
		this.kafkaTemplate = kafkaTemplate;
	}
	
	
	@Override
	public Boolean push(String topic, String payload) {

		LOGGER.info("==> Push topic='{}' sending payload='{}' to ", topic, payload);
        kafkaTemplate.send(topic, payload);
        LOGGER.info("==> Push Finalizado. ");
        return true;
	}

}
