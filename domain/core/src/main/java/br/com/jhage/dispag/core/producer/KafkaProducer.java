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
@Component("KAFKA_PRODUCER_PRD")
public class KafkaProducer implements Pusher{

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class); 
	
	
    private KafkaTemplate<Integer, String> kafkaTemplate;
	
    @Autowired
	public KafkaProducer(KafkaTemplate<Integer, String> kafkaTemplate) {
		
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
