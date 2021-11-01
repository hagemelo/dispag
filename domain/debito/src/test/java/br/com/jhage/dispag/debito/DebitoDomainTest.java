package br.com.jhage.dispag.debito;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.jhage.dispag.core.helper.TimerHelper;
import br.com.jhage.dispag.debito.domain.DebitoDomain;
import br.com.jhage.dispag.debito.helper.TimerHelperDebitoDomainImp;
import br.com.jhage.dispag.debito.repository.DebitoRepository;
import br.com.jhage.dispag.debito.service.PusherService;


public class DebitoDomainTest {

	private DebitoDomain debitoDomain;
	@SuppressWarnings({ "rawtypes", "unused" })
	private ConsumerRecord consumerRecord;
	private DebitoRepository repository;
	private PusherService pusher;
	private TimerHelper timer;
	
	@Before
	public void prepared() {
		System.out.println("==> Preparar o Teste com os objetos importantes para a availação do 'resgistrarDebito'.");
		instanciarObjetoDebitoDomain();
		instanciarObjetodePayloaddoCustomer();
	}
	
	@Test
	public void deveDeixarONovoDebitoPendenteaoRegistrarDebito(){
		
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("XXXXXXXXXXXXXXXXXXXX_INICIANDO_TESTE_XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("XXX_deveDeixarONovoDebitoPendenteaoRegistrarDebito_XXXXXXXXXXXXXXX");
		this.debitoDomain.resgistrarDebito(this.consumerRecord);
		Assert.assertTrue(this.debitoDomain.getModel().isPendente());
		System.out.println("XXXXXXXXXXXXXXXXXXXX_FINALIZANDO_TESTE_XXXXXXXXXXXXXXXXXXXXXXXXXXX");
	}

	@Test
	public void deveExecutarPushVerificardor() {
		
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("XXXXXXXXXXXXXXXXXXXX_INICIANDO_TESTE_XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("XXXXXXXXXXXXXXXXX_deveExecutarPushVerificardor_XXXXXXXXXXXXXXXXXXX");
		this.debitoDomain.resgistrarDebito(this.consumerRecord);
		Assert.assertTrue(((kafkaPusherImp)this.pusher).executouPosh());
		System.out.println("XXXXXXXXXXXXXXXXXXXX_FINALIZANDO_TESTE_XXXXXXXXXXXXXXXXXXXXXXXXXXX");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void instanciarObjetodePayloaddoCustomer() {
		
		System.out.println("==> Criar objeto com o Payload do Customer kafka.");
		this.consumerRecord = new ConsumerRecord("Teste", 0, 0, null,  PAYLOAD);
	}
	
	private void instanciarObjetoDebitoDomain() {
		
		System.out.println("==> Criar objeto alvo do teste.");
		this.repository =  DebitoRepositoryImp.newInstance();
		this.pusher = kafkaPusherImp.newInstance();
		this.timer = new TimerHelperDebitoDomainImp();
		this.debitoDomain = new DebitoDomain(3, this.repository, this.pusher, "Teste", this.timer);
	}
	
	private static final String PAYLOAD = "{ \"valor\":25,	\"marcacao\":\"PAGAMENTO DE BESTEIRA\", \"vencimento\":\"25/06/2021\" }";
	
	
}
