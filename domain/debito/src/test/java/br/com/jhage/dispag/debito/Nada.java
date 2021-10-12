package br.com.jhage.dispag.debito;

public class Nada {

	public Boolean test() {
		Boolean result = true;
		boolean val1 = false;
		result |= val1;
		boolean val2 = true;
		result &= val2;
		
		return result;
	}
	
	public static void main(String[] args) {
		
		Nada nada = new Nada();
		System.out.println(nada.test());
	}
	
}
