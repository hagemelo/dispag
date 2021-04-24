package br.com.jhage.dispag.pagardebito;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.jhage.dispag.pagardebito.repository.DebitosRepository;

/**
 * 
 * @author Alexsander Melo
 * @since 06/02/2021
 *
 */

//@SpringBootTest(classes = NovoDebitoApplication.class)
//@DirtiesContext
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest()
@ActiveProfiles("test")
@ContextConfiguration(classes={DebitosRepository.class, PagarDebitoKafkaConsumerTest.class})
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = { "listeners=PLAINTEXT://localhost:9092", "port=9092" })
public class PagarDebitoConsumerServiceTest {

	@Autowired
	private PagarDebitoConsumerServiceTest service;

	@Autowired
	private KafkaProducer producer;
	
	@Value("${kafka.topic}")
	private String topic;

	@Test
	public void test() throws Exception{

		System.out.println("TEste teste ");
		producer.send(topic, "Sending with own simple KafkaProducer");
//		service.run(""); 
		System.out.println("Teste");
		Assert.assertEquals("file", "");
		Assert.assertEquals("file", "");
	}
}
