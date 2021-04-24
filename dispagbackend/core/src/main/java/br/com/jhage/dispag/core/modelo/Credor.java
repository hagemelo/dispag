package br.com.jhage.dispag.core.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jhage.dispag.core.constante.Estado;
import br.com.jhage.dispag.core.constante.Tipo;
import br.com.jhage.dispag.core.exception.ConverterToStringException;

/**
 * 
 * @author Alexsander Melo
 * @since 04/02/2021
 * 
 */
@Entity
@Table(name = "TB_CREDOR")
public class Credor implements JhageEntidade<Credor> {

	private static final long serialVersionUID = 1L;

	@Version
	Integer versao;

	@Id
	@Column(name = "CREDOR_ID", nullable = false)
	@SequenceGenerator(name = "crdid", sequenceName = "GEN_CREDOR_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "crdid")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "estado")
	private Estado estado;

	@Column(name = "descricao")
	private String descricao;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo")
	private Tipo tipo;
	
	@OneToMany(mappedBy = "credor", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Debitos> debitos;
	
	public Credor(String descricao, String tipo) {

		this.descricao = descricao;
		this.tipo = Tipo.get(tipo);
		this.debitos = new HashSet<Debitos>();
		this.estado = Estado.PENDENTE;
	}
	
	public Credor(String descricao, Tipo tipo) {

		this.descricao = descricao;
		this.tipo = tipo;
		this.debitos = new HashSet<Debitos>();
		this.estado = Estado.PENDENTE;
	}
	
	public Credor() {
		
		this.descricao = "";
		this.tipo = Tipo.AVULSOS;
	}

	@Override
	public Long getId() {

		return this.id;
	}
	
	public Estado getEstado() {
		return estado;
	}

	@Override
	public String converterToString() throws ConverterToStringException {

		StringBuffer buffer = new StringBuffer()
				.append(this.tipo.getDescricao())
				.append(SEPARADOR)
				.append(this.descricao);
		return buffer.toString();
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDescricao() {
		return descricao;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public Set<Debitos> getDebitos() {
		
		if (this.debitos == null)
			this.debitos = new HashSet<Debitos>();
		return debitos;
	}
	
	public Credor add(Debitos debitos) {
		
		this.debitos.add(debitos);
		return this;
	}

	public void aprovar() {
		
		this.estado = Estado.APROVADO;
	}
	
	public void rejeitar() {
		
		this.estado = Estado.REJEITADO;
	}
	
	@JsonIgnore
	public String getJsonValue() throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}

	@Override
	public int hashCode() {

		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? ZERO : id.hashCode());
		result = prime * result + ((this.descricao == null) ? ZERO : this.descricao.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Credor)) {
			return false;
		}
		Credor other = (Credor) obj;
		return super.equals(obj) && this.id.equals(other.id) && this.descricao.equals(other.descricao);
	}

}
