package br.com.jhage.dispag.core.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer implements Pusher{

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class); 
	
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	
	
	@Override
	public void push(String topic, String payload) {

		LOGGER.info("==> Push topic='{}' sending payload='{}' to ", topic, payload);
        kafkaTemplate.send(topic, payload);
	}

        

}
