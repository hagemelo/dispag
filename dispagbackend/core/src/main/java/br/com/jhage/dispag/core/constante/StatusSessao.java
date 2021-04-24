package br.com.jhage.dispag.core.constante;


/**
 * 
 * @author Alexsander Melo
 * @since 11/03/2021
 *
 */

public enum StatusSessao {

	ABERTA("ABERTA"), 
	FECHADO("FECHADO");
	
	private final String descricao;
	
	StatusSessao(final String descricao) {

		this.descricao = descricao;
	}
	
	public String getDescricao() {
		
		return this.descricao;
	}
}
