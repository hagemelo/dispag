package br.com.jhage.dispag.pagardebito.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Alexsander Melo
 *
 */
public class CreateObjectDebitoException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT= "ERRRO_ACAO_ABRUPTA";
	
	public Logger inicializarLogger() {
		return LogManager.getLogger(CreateObjectDebitoException.class);
	}

	public CreateObjectDebitoException() {
		super(DEFAULT);
		this.inicializarLogger().error(DEFAULT);
	}
	
	public CreateObjectDebitoException(String message) {
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
