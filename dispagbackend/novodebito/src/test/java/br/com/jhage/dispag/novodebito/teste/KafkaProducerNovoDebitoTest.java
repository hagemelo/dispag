package br.com.jhage.dispag.novodebito.teste;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Alexsander Melo
 * @since 02/04/2021
 *
 */

@Component
public class KafkaProducerNovoDebitoTest {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerNovoDebitoTest.class);

	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String payload) {
        LOGGER.info("sending payload='{}' to topic='{}'", payload, topic);
        kafkaTemplate.send(topic, payload);
    }

    
    
}
