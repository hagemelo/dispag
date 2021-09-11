package br.com.jhage.dispag.core.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Alexsander Melo
 * @since 03/11/2018
 * 
 */
public class ConverterToStringException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT= "ERRRO_ACAO_ABRUPTA";
	
	public Logger inicializarLogger() {
		return LogManager.getLogger(ConverterToStringException.class);
	}

	public ConverterToStringException() {
		super(DEFAULT);
		this.inicializarLogger().error(DEFAULT);
	}
	
	public ConverterToStringException(String message) {
		super(message);
		this.inicializarLogger().error(message);
	}
	
	public ConverterToStringException(final Exception cause) {
		super(cause);
		this.inicializarLogger().error(cause.getMessage());
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
