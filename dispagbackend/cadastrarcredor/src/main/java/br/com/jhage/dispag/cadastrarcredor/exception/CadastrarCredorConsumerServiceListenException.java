package br.com.jhage.dispag.cadastrarcredor.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author Alexsander Melo
 * @since 11/04/2021
 *
 */

public class CadastrarCredorConsumerServiceListenException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private static final String DEFAULT= "ListenException Erro ao Cadastrar Credor::";
	
	public Logger inicializarLogger() {
		return LogManager.getLogger(LoadCredorException.class);
	}

	public CadastrarCredorConsumerServiceListenException() {
		super(DEFAULT);
		this.inicializarLogger().error(DEFAULT);
	}
	
	public CadastrarCredorConsumerServiceListenException(String message) {
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
