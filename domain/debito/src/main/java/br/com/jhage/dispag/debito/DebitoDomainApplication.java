package br.com.jhage.dispag.debito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import br.com.jhage.dispag.core.conf.KafkaConfig;
import br.com.jhage.dispag.debito.repository.DebitoRepository;

/**
 * 
 * @author Alexsander Melo
 * @since 09/09/2021
 *
 */

@EnableAutoConfiguration
@SpringBootApplication
@Import(value= {KafkaConfig.class})
public class DebitoDomainApplication {

	public static void main(String[] args) {
		SpringApplication.run(DebitoDomainApplication.class, args).close();
	}
}




