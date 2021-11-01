package br.com.jhage.dispag.core.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jhage.dispag.core.constante.Estado;
import br.com.jhage.dispag.core.constante.Mes;

/**
 * 
 * @author Alexsander Melo
 * @since 09/09/2021
 *
 */
public class OrcamentoDTO {
	
	@Enumerated(EnumType.STRING)
	private Estado estado;

	@Enumerated(EnumType.STRING)
	private Mes mes;
	
	private Integer ano;
	
	private Double receita;

	private Double basicos;

	private Double recorrentes;

	private Double avulsos;
	
	private UsuarioDTO usuario;
	
	
	public OrcamentoDTO(Estado estado, Mes mes, Integer ano, Double receita, Double basicos, Double recorrentes,
			Double avulsos, UsuarioDTO usuario) {

		this.estado = estado; 
		this.mes =  mes;
		this.ano = ano; 
		this.receita = receita; 
		this.basicos = basicos; 
		this.recorrentes = recorrentes;
		this.avulsos = avulsos; 
		this.usuario = usuario;
		
	}
	
	public OrcamentoDTO() {}
	

	public Estado getEstado() {
		return estado;
	}

	public Mes getMes() {
		return mes;
	}

	public int getAno() {
		return ano;
	}

	public Double getReceita() {
		return receita;
	}

	public Double getBasicos() {
		return basicos;
	}

	public Double getRecorrentes() {
		return recorrentes;
	}

	public Double getAvulsos() {
		return avulsos;
	}

	public UsuarioDTO getUsuario() {
		return usuario;
	}

	@JsonIgnore
	public String getJsonValue() throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}
}
