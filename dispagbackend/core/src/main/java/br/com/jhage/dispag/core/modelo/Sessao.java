package br.com.jhage.dispag.core.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jhage.dispag.core.constante.StatusSessao;
import br.com.jhage.dispag.core.exception.ConverterToStringException;

/**
 * 
 * @author Alexsander Melo
 * @since 11/03/2021
 *
 */
@Entity
@Table(name = "TB_SESSAO")
public class Sessao implements JhageEntidade<Sessao> {

	private static final long serialVersionUID = 1L;

	@Version
	Integer versao;

	@Id
	@Column(name = "SES_ID", nullable = false)
	@SequenceGenerator(name = "sesid", sequenceName = "GEN_DORC_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sesid")
	private Long id;

	private String uuid;
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "USE_ID", referencedColumnName = "USE_ID")
	private Usuario usuario;
	
	private StatusSessao statusSessao;
	
	public Sessao() {
		
		this.statusSessao = StatusSessao.ABERTA;
	}
	
	public Sessao(String uuid) {
		
		this.uuid = uuid;
		this.statusSessao = StatusSessao.ABERTA;
	}
	
	@Override
	public Long getId() {

		return this.id;
	}

	public String getUuid() {
		return uuid;
	}

	public StatusSessao getStatusSessao() {
		return statusSessao;
	}
	
	public boolean estaAberta() {
		
		return StatusSessao.ABERTA.equals(this.statusSessao);
	}
	
	public void fechar() {
		
		this.statusSessao = StatusSessao.FECHADO;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public Sessao add(Usuario usuario) {
		
		this.usuario = usuario;
		return this;
	}
	
	@JsonIgnore
	public String getJsonValue() throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}

	@Override
	public String converterToString() throws ConverterToStringException {
		String result = "";
		
		StringBuffer buffer = new StringBuffer()
				.append("Sessao uuid")
				.append(SEPARADOR)
				.append(this.uuid)
				.append(SEPARADOR)
				.append(this.statusSessao.getDescricao());
		result = buffer.toString();
		return result;
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? ZERO : id.hashCode());
		result = prime * result + ((this.uuid == null) ? ZERO : uuid.hashCode());
		result = prime * result + ((this.statusSessao == null) ? ZERO : statusSessao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Sessao)) {
			return false;
		}
		Sessao other = (Sessao) obj;
		return super.equals(obj) && this.id.equals(other.id) && this.uuid.equals(other.uuid)
				&& this.statusSessao.equals(other.statusSessao);
	}
}
