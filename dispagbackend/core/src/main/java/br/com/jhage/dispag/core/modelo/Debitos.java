package br.com.jhage.dispag.core.modelo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jhage.dispag.core.constante.Estado;
import br.com.jhage.dispag.core.constante.Status;
import br.com.jhage.dispag.core.exception.ConverterToStringException;
import br.com.jhage.dispag.core.exception.FormatDateHelperException;
import br.com.jhage.dispag.core.exception.NumberHelpException;
import br.com.jhage.dispag.core.helper.FormatDateHelper;
import br.com.jhage.dispag.core.helper.NumberHelp;

/**
 * 
 * @author Alexsander Melo
 * @since 04/02/2021
 * 
 * 
 */
@Entity
@Table(name = "TB_DEBITOS")
public class Debitos implements JhageEntidade<Debitos> {

	private static final long serialVersionUID = 1L;

	@Version
	Integer versao;

	@Id
	@Column(name = "DEBITO_ID", nullable = false)
	@SequenceGenerator(name = "debid", sequenceName = "GEN_DEBITO_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "debid")
	private Long id;

	@Column(name = "VALOR")
	private Double valor;

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado")
	private Estado estado;

	@Column(name = "MARCACAO")
	private String marcacao;

	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "CREDOR_ID", referencedColumnName = "CREDOR_ID")
	private Credor credor;
	
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "ORC_ID", referencedColumnName = "ORC_ID")
	private Orcamento orcamento;

	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "Brazil/East")
	@Temporal(TemporalType.DATE)
	private Date vencimento;

	public Debitos() {

		this.valor = Double.valueOf(ZERO);
		this.vencimento = new Date();
		this.status = Status.AVENCER;
		this.estado = Estado.PENDENTE;
	}

	public Debitos(Double valor, String status, String vencimento) {

		this.valor = 0.;
		this.vencimento = new Date();
		this.status = Status.AVENCER;
		this.estado = Estado.PENDENTE;
	}

	@Override
	public Long getId() {

		return this.id;
	}

	public Double getValor() {
		return valor;
	}

	public Status getStatus() {
		return status;
	}

	public Credor getCredor() {
		return credor;
	}

	public Date getVencimento() {
		return vencimento;
	}
	
	@JsonIgnore
	public String getVencimentoString() {
		try {
			return  FormatDateHelper.getInstance().converterDataParaCaracter(this.vencimento);
		} catch (FormatDateHelperException e) {

			e.printStackTrace();
			return "";
		}
	}

	public Estado getEstado() {
		return estado;
	}
	
	public Orcamento getOrcamento() {
		return orcamento;
	}

	public Debitos add(Credor credor) {

//		credor.add(this);
		this.credor = credor;
		return this;
	}
	
	public Debitos add(Orcamento orcamento) {

//		credor.add(this);
		this.orcamento = orcamento;
		return this;
	}

	public String getMarcacao() {
		return marcacao;
	}

	@JsonProperty
	public String valorString() throws NumberHelpException {

		return NumberHelp.parseDoubleToString(this.getValor());
	}

	@JsonIgnore
	public String getJsonValue() throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}

	public void pagar() {

		this.status = Status.PAGO;
	}

	public void marcarComoAtrasado() {

		this.status = Status.ATRASADO;
	}
	
	public void aprovar() {
		
		this.estado = Estado.APROVADO;
	}
	
	public void rejeitar() {
		
		this.estado = Estado.REJEITADO;
	}
	
	@Override
	public String converterToString() throws ConverterToStringException {
		String result = "";
		try {
			StringBuffer buffer = new StringBuffer().append(this.credor.converterToString()).append(SEPARADOR)
					.append(this.marcacao).append(SEPARADOR)
					.append(FormatDateHelper.getInstance().converterDataParaCaracter(this.vencimento)).append(SEPARADOR)
					.append(NumberHelp.parseDoubleToString(this.valor));
			result = buffer.toString();
		} catch (NumberHelpException | FormatDateHelperException e) {
			e.printStackTrace();
			throw new ConverterToStringException();
		}
		return result;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? ZERO : id.hashCode());
		result = prime * result + ((this.vencimento == null) ? ZERO : vencimento.hashCode());
		result = prime * result + ((this.valor == null) ? ZERO : this.valor.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Debitos)) {
			return false;
		}
		Debitos other = (Debitos) obj;
		return super.equals(obj) && this.id.equals(other.id) && this.valor.equals(other.valor);
	}
}
