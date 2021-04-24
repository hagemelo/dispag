package br.com.jhage.dispag.core.constante;

/**
 * 
 * @author Alexsander Melo
 * @since 07/03/2021
 *
 */

public enum Estado {

	PENDENTE("PENDENTE"), APROVADO("APROVADO"), REJEITADO("PAGO");

	private final String descricao;

	Estado(final String descricao) {

		this.descricao = descricao;
	}

	public String getDescricao() {

		return this.descricao;
	}
}
