package br.com.jhage.dispag.novodebito.teste;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.jhage.dispag.novodebito.event.NovoDebito;
import br.com.jhage.dispag.novodebito.service.NovoDebitoConsumerService;

/**
 * 
 * @author Alexsander Melo
 * @since 02/04/2021
 *
 *
 */
 ///@Import(value= {NovoDebitoConsumerService.class, CredorRepository.class, DebitosRepository.class})

//@ActiveProfiles("test")@SpringBootTest(classes = NovoDebitoApplication.class)
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest()
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
@Import(value = {NovoDebitoConsumerService.class })
@EnableJpaRepositories(basePackages = "br.com.jhage.dispag.novodebito.repository")
public class NovoDebitoTest {
	
	
	@Autowired
    private NovoDebitoConsumerService consumer;

    @Autowired
    private KafkaProducerNovoDebitoTest producer;

    @Value("${kafka.topic}")
    private String topic;


	@Test
	public void deveFuncionar() {

		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		producer.send(topic, PAYLOAD);
		NovoDebito novo = new NovoDebito();
//		System.out.println(novo.getJsonValue());
		System.out.println(PAYLOAD);
	}

//	public static void main(String[] args) throws JsonProcessingException {
//
//		new NovoDebitoTest().deveFuncionar();
//	}
	
	
	private static final String PAYLOAD = "{" + 
			"	\"valor\":25," + 
			"	\"marcacao\":\"PAGAMENTO DE BESTEIRA\"," + 
			"	\"credor\":{" + 
			"		\"descricao\":\"DINHEIRO NA CARTEIRA\"," + 
			"		\"tipo\":\"AVULSOS\"" + 
			"	}," + 
			"	\"vencimento\":\"25/06/2021\"\r\n" + 
			"}";
}
