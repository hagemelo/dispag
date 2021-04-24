package br.com.jhage.dispag.core.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Alexsander Melo
 * @since 02/12/2018
 * 
 */
public class LastNameException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private static final String DEFAULT= "ERRRO_ACAO_ABRUPTA";
	
	public Logger inicializarLogger() {
		return LogManager.getLogger(LastNameException.class);
	}

	public LastNameException() {
		super(DEFAULT);
		this.inicializarLogger().error(DEFAULT);
	}
	
	public LastNameException(String message) {
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
