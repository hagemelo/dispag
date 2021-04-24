package br.com.jhage.dispag.novodebito.event;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.jhage.dispag.core.modelo.Debitos;
import br.com.jhage.dispag.core.modelo.Sessao;

/**
 * 
 * @author Alexsander Melo
 * @since 31/03/2021
 * 
 * 
 */

public class NovoDebito implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Debitos debitos;
	private Sessao sessao;
	
	public NovoDebito() {
		
		this.debitos = new Debitos();
		this.sessao = new Sessao();
	}

	public Debitos getDebitos() {
		return debitos;
	}

	public void setDebitos(Debitos debitos) {
		this.debitos = debitos;
	}

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}
	
	@JsonIgnore
	public String getJsonValue() throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		return mapper.writeValueAsString(this);
	}

}
