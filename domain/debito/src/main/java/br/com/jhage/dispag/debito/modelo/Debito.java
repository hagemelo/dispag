package br.com.jhage.dispag.debito.modelo;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jhage.dispag.core.constante.Estado;
import br.com.jhage.dispag.core.constante.Status;
import br.com.jhage.dispag.core.dto.DebitoDTO;
import br.com.jhage.dispag.core.exception.ConverterToStringException;
import br.com.jhage.dispag.core.exception.FormatDateHelperException;
import br.com.jhage.dispag.core.exception.NumberHelpException;
import br.com.jhage.dispag.core.helper.FormatDateHelper;
import br.com.jhage.dispag.core.helper.NumberHelp;
import br.com.jhage.dispag.core.modelo.JhageEntidade;

/**
 * 
 * @author Alexsander Melo
 * @since 09/09/2021
 * 
 * 
 */
@Entity
@Table(name = "DISPAG_DEBITOS")
public class Debito implements JhageEntidade<Debito> {

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

	@Column(name = "Credor")
	private String credor;

	@Column(name = "Orcamento")
	private String orcamento;

	@JsonFormat(pattern = "dd/MM/yyyy", timezone = "Brazil/East")
	@Temporal(TemporalType.DATE)
	private Date vencimento;
	
	public Debito() {}

	public Debito(DebitoDTO debitoDTO) {

		this.valor = debitoDTO.getValor();
		this.vencimento = debitoDTO.getVencimento();
		this.status = Status.AVENCER;
		this.estado = Estado.PENDENTE;
	}

	
	public Long getById() {

		return this.id;
	}

	@JsonIgnore
	public String getVencimentoString() {
		try {
			return FormatDateHelper.getInstance().converterDataParaCaracter(this.vencimento);
		} catch (FormatDateHelperException e) {

			e.printStackTrace();
			return "";
		}
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
	
	public Boolean isPendente() {
		
		Boolean result = false;
		result |=  Estado.PENDENTE.equals(this.estado);
		return result;
	}

	@Override
	public String converterToString() throws ConverterToStringException {
		String result = "";
		try {
			StringBuffer buffer = new StringBuffer().append(this.credor).append(SEPARADOR).append(this.marcacao)
					.append(SEPARADOR).append(FormatDateHelper.getInstance().converterDataParaCaracter(this.vencimento))
					.append(SEPARADOR).append(NumberHelp.parseDoubleToString(this.valor));
			result = buffer.toString();
		} catch (NumberHelpException | FormatDateHelperException e) {
			e.printStackTrace();
			throw new ConverterToStringException();
		}
		return result;
	}

	public boolean isValid() {

		return this.valor != null && this.marcacao != null && this.marcacao.length() != 0 && this.credor != null
				&& this.orcamento != null && this.vencimento != null;
	}

	@Override
	public int hashCode() {

		try {
			
			MessageDigest m = MessageDigest.getInstance("MD5");
			m.update(String.valueOf(gerarPrimeValue()).getBytes(), 0, String.valueOf(gerarPrimeValue()).length());
			return new BigInteger(1, m.digest()).intValue();
			
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
			return 1;
		}
	}

	private int gerarPrimeValue() {

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
		if (!(obj instanceof Debito)) {
			return false;
		}
		Debito other = (Debito) obj;
		return super.equals(obj) && this.id.equals(other.id) && this.valor.equals(other.valor);
	}
}
