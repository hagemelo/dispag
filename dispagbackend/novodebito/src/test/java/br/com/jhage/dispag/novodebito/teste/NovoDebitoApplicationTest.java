package br.com.jhage.dispag.novodebito.teste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import br.com.jhage.dispag.modelo.KafkaProducerConsumerApplication;

@EnableAutoConfiguration
@SpringBootApplication
@EntityScan( basePackages = {"br.com.jhage.dispag.core.modelo"} )
public class NovoDebitoApplicationTest {

	
	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerConsumerApplication.class, args);
	}
	
}
