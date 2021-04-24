package br.com.jhage.dispag.core.helper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.jhage.dispag.core.exception.NumberHelpException;

/**
 * 
 * @author Alexsander Melo
 * @since 25/01/2017
 *
 */
@Configuration
public class NumberHelp {

	@Bean
	public NumberHelp getInstance() {
		
		return new NumberHelp();
	}
	
	public static String parseDoubleToString(Double valor) throws NumberHelpException{
		
		StringBuffer build = new StringBuffer();
		try{
			
			build.append(String.format("%,.2f", valor));
		}catch (Exception e) {

			throw new NumberHelpException();
		}
		return build.toString().trim();
	}
	
}
