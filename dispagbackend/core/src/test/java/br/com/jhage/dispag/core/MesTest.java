package br.com.jhage.dispag.core;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import br.com.jhage.dispag.core.constante.Mes;

@Ignore
public class MesTest {
	
	
	@Test
	public void deveRetornarMesAtual() throws Exception {
		
		Mes find  = Mes.now();
//		System.out.println("Mes:" + find);
		Assert.assertEquals(Mes.ABRIL, find);
		
		System.out.println(Mes.ABRIL.toString());
		System.out.println(Mes.ABRIL.name());
	}
	

}
