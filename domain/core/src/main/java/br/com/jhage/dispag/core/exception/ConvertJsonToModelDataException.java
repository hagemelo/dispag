package br.com.jhage.dispag.core.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Alexsander Melo
 * @since 05/02/2021
 *
 */

public class ConvertJsonToModelDataException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT= "ERRRO_ACAO_ABRUPTA";
	
	public Logger inicializarLogger() {
		return LogManager.getLogger(ConvertJsonToModelDataException.class);
	}

	public ConvertJsonToModelDataException() {
		super(DEFAULT);
		this.inicializarLogger().error(DEFAULT);
	}
	
	public ConvertJsonToModelDataException(String message) {
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
