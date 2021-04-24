package br.com.jhage.dispag.core.modelo;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jhage.dispag.core.exception.ConverterToStringException;

/**
 * 
 * @author Alexsander Melo
 * @since 11/03/2021
 *
 */
@Entity
@Table(name = "TB_USUARIO")
public class Usuario implements JhageEntidade<Credor> {

	private static final long serialVersionUID = 1L;

	@Version
	Integer versao;

	@Id
	@Column(name = "USE_ID", nullable = false)
	@SequenceGenerator(name = "useid", sequenceName = "GEN_USE_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "useid")
	private Long id;
	
	private String nome;
	
	private String login;
	
	private String senha;
	
	@OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Orcamento> orcamentos;
	
	public Usuario() {
		
		
	}

	@Override
	public Long getId() {

		return this.id;
	}

	public String getNome() {
		return nome;
	}

	public String getLogin() {
		return login;
	}

	public String getSenha() {
		return senha;
	}

	public Set<Orcamento> getOrcamentos() {
		return orcamentos;
	}
	
	@Override
	public String getJsonValue() throws JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}
	
	
	@Override
	public String converterToString() throws ConverterToStringException {

		StringBuffer buffer = new StringBuffer()
				.append(this.nome)
				.append(SEPARADOR)
				.append(this.login);
		return buffer.toString();
	}
	
	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? ZERO : id.hashCode());
		result = prime * result + ((this.nome == null) ? ZERO : nome.hashCode());
		result = prime * result + ((this.login == null) ? ZERO : login.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Usuario)) {
			return false;
		}
		Usuario other = (Usuario) obj;
		return super.equals(obj) && this.id.equals(other.id) && this.nome.equals(other.nome)
				&& this.login.equals(other.login);
	}
	
}
