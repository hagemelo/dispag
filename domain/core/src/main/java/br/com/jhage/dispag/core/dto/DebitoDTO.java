package br.com.jhage.dispag.core.dto;

import java.util.Date;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jhage.dispag.core.constante.Estado;
import br.com.jhage.dispag.core.constante.Status;

/**
 * 
 * @author Alexsander Melo
 * @since 09/09/2021
 *
 */
public class DebitoDTO {

	private Integer id;
	
	private Double valor;

	@Enumerated(EnumType.STRING)
	private Status status;

	@Enumerated(EnumType.STRING)
	private Estado estado;

	private String marcacao;

	private CredorDTO credor;

	private OrcamentoDTO orcamento;

	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "Brazil/East")
	private Date vencimento;
	
	public DebitoDTO(Double valor, Status status, Estado estado, String marcacao, CredorDTO credor,
			OrcamentoDTO orcamento, Date vencimento) {

		this.valor = valor;
		this.status = status;
		this.estado = estado;
		this.marcacao = marcacao;
		this.credor = credor;
		this.orcamento = orcamento;
		this.vencimento = vencimento;
	}
	
	public DebitoDTO() {}

	public Double getValor() {

		return valor;
	}

	public Status getStatus() {

		if (this.status == null) {

			this.status = Status.AVENCER;
		}
		return status;
	}

	public Estado getEstado() {

		if (this.estado == null) {

			this.estado = Estado.PENDENTE;
		}
		return estado;
	}

	public String getMarcacao() {
		return marcacao;
	}

	public CredorDTO getCredor() {
		return credor;
	}

	public OrcamentoDTO getOrcamento() {
		return orcamento;
	}

	public Date getVencimento() {
		return vencimento;
	}

	@JsonIgnore
	public String getJsonValue() throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}

}
