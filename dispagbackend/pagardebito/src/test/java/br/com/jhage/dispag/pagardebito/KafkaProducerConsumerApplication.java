package br.com.jhage.dispag.pagardebito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EnableAutoConfiguration
@EntityScan( basePackages = {"br.com.jhage.dispag.core.modelo", "br.com.jhage.dispag.novodebito.repository"} )
public class KafkaProducerConsumerApplication {

	public static void main(String[] args) {
        SpringApplication.run(KafkaProducerConsumerApplication.class, args);
    }
}
