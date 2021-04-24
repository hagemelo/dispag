package br.com.jhage.dispag.core.constante;

/**
 * 
 * @author Alexsander Melo
 * @since 04/02/2021
 *
 */

public enum Status {

	AVENCER("A VENCER"), ATRASADO("ATRASADO"), PAGO("PAGO");

	private final String descricao;

	Status(final String descricao) {

		this.descricao = descricao;
	}

	public String getDescricao() {

		return this.descricao;
	}
}
