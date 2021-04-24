package br.com.jhage.dispag.core;



import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import br.com.jhage.dispag.core.constante.Mes;
import br.com.jhage.dispag.core.constante.Tipo;

@Ignore
public class TipoTest {

	@Test
	public void deveRetornarAvulso() throws Exception {
		
		String find  = "AVULSOS";
		
		Assert.assertEquals(Tipo.AVULSOS, Tipo.get(find));
		
	}
	
	@Test
	public void deveRetornarMesAtual() throws Exception {
		
		Mes find  = Mes.now();
//		System.out.println("Mes:" + find);
		Assert.assertEquals(Mes.MARCO, find);
		
	}
	
}
