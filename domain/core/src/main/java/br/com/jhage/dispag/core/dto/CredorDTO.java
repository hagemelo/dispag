package br.com.jhage.dispag.core.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jhage.dispag.core.constante.Tipo;

/**
 * 
 * @author Alexsander Melo
 * @since 09/09/2021
 *
 */
public class CredorDTO {
	
	String descricao;
	@Enumerated(EnumType.STRING)
	Tipo tipo;
	
	
	public CredorDTO(String descricao, Tipo tipo) {
		
		this.tipo = tipo;
		this.descricao = descricao;
	}


	public String getDescricao() {
		return descricao;
	}


	public Tipo getTipo() {
		return tipo;
	}
	
	
	@JsonIgnore
	public String getJsonValue() throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}

}
