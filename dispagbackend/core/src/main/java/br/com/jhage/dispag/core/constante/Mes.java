package br.com.jhage.dispag.core.constante;

import java.time.LocalDate;

public enum Mes {
	JANEIRO,
	FEVEREIRO,
	MARCO,
	ABRIL,
	MAIO,
	JUNHO,
	JULHO,
	AGOSTO,
	SETEMBRO,
	OUTUBRO,
	NOVEMBRO,
	DEZEMBRO;	
	
	public static Mes get(String find) {

		Mes result = Mes.JANEIRO;
		try{
			result  = valueOf(Mes.class, find);
		}catch (IllegalArgumentException e) {
			
			System.out.println("Mes Nao Encontrado");
		}
		return result;
	}
	
	public static Mes get(int find) {

		Mes result = Mes.JANEIRO;
		try{
			result  = values()[find];
		}catch (IllegalArgumentException e) {
			
			System.out.println("Mes Nao Encontrado");
		}
		return result;
	}
	
	public static Mes now() {
		
		return get(LocalDate.now().getMonth().getValue()-1);
	}
	

}
