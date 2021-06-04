package br.com.jhage.dispag.efetivarnovoorcamento.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Alexsander Melo
 * @since 12/05/2021
 *
 */

public class LoadOrcamentoException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT= "ERRRO_ACAO_ABRUPTA";
	
	public Logger inicializarLogger() {
		return LogManager.getLogger(LoadOrcamentoException.class);
	}

	public LoadOrcamentoException() {
		super(DEFAULT);
		this.inicializarLogger().error(DEFAULT);
	}
	
	public LoadOrcamentoException(String message) {
		super(message);
		this.inicializarLogger().error(message);
	}
	
	@Override
	public String getMessage() {

		String message = super.getMessage();
		if (message == null || message.isEmpty()) {
			message = DEFAULT;
		}
		return message;
	}

}
