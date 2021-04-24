package br.com.jhage.dispag.core.constante;

/**
 * 
 * @author Alexsander Melo
 * @since 01/12/2018
 *
 */
public enum Tipo {

	BASICOS("Basico"), RECORRENTES("Recorrentes"), AVULSOS("Avulsos");

	private final String descricao;

	Tipo(final String descricao) {

		this.descricao = descricao;
	}
	
	public String getDescricao() {
		
		return this.descricao;
	}
	
	public static Tipo get(String find) {

		Tipo result = Tipo.AVULSOS;
		try{
			result  = valueOf(Tipo.class, find);
		}catch (IllegalArgumentException e) {
			
			System.out.println("Tamanho Nao Encontrado");
		}
		return result;
	}

}
