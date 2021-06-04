package br.com.jhage.dispag.novoorcamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Import;

import br.com.jhage.dispag.core.conf.KafkaConfig;

/**
 * 
 * @author Alexsander Melo
 * @since 10/05/2021
 *
 */
@EnableAutoConfiguration
@SpringBootApplication
@EntityScan( basePackages = {"br.com.jhage.dispag.core.modelo"} )
@Import(value= {KafkaConfig.class})
public class NovoOrcamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(NovoOrcamentoApplication.class, args).close();
	}

}
