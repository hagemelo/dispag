package br.com.jhage.dispag.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Alexsander Melo
 * @since 09/09/2021
 *
 */
public class UsuarioDTO {

	
	private String nome;
	
	private String login;
	
	private String senha;
	
	public UsuarioDTO(String nome, String login, String senha) {
		
		this.nome = nome;
		this.login = login;
		this.senha = senha;
	}
	
	public UsuarioDTO() {}
	
	public String getNome() {
		return nome;
	}

	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}

	@JsonIgnore
	public String getJsonValue() throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}
	
}
