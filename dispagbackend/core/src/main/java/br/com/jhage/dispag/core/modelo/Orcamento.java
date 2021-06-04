package br.com.jhage.dispag.core.modelo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.jhage.dispag.core.constante.Estado;
import br.com.jhage.dispag.core.constante.Mes;
import br.com.jhage.dispag.core.exception.ConverterToStringException;
import br.com.jhage.dispag.core.exception.NumberHelpException;
import br.com.jhage.dispag.core.helper.NumberHelp;

/**
 * 
 * @author Alexsander Melo
 * @since 15/02/2021
 *
 */
@Entity
@Table(name = "TB_ORCAMENTO")
public class Orcamento implements JhageEntidade<Orcamento> {

	private static final long serialVersionUID = 1L;

	@Version
	Integer versao;

	@Id
	@Column(name = "ORC_ID", nullable = false)
	@SequenceGenerator(name = "orcid", sequenceName = "GEN_DORC_ID", allocationSize = 1, initialValue = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orcid")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "ESTADO")
	private Estado estado;

	@Enumerated(EnumType.STRING)
	@Column(name = "MES")
	private Mes mes;
	
	@Column(name = "ANO")
	private int ano;
	
	@Column(name = "RECEITA")
	private Double receita;

	@Column(name = "BASICOS")
	private Double basicos;

	@Column(name = "RECORRENTES")
	private Double recorrentes;

	@Column(name = "AVULSOS")
	private Double avulsos;
	
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
	@JoinColumn(name = "USE_ID", referencedColumnName = "USE_ID")
	private Usuario usuario;

	public Orcamento() {

		this.receita = Double.valueOf(ZERO);
		this.recorrentes = Double.valueOf(ZERO);
		this.basicos = Double.valueOf(ZERO);
		this.avulsos = Double.valueOf(ZERO);
		this.mes = Mes.now();	
		this.debitos = new HashSet<Debitos>();
		this.estado = Estado.PENDENTE;
	}
	
	@OneToMany(mappedBy = "credor", fetch = FetchType.LAZY, orphanRemoval = true)
	private Set<Debitos> debitos;

	@Override
	public Long getId() {

		return this.id;
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

	public Estado getEstado() {
		return estado;
	}

	public void aprovar() {
		
		this.estado = Estado.APROVADO;
	}
	
	public void rejeitar() {
		
		this.estado = Estado.REJEITADO;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public Set<Debitos> getDebitos() {
		return debitos;
	}

	public Double totalOrcado() {
		
		return this.basicos + this.avulsos + this.recorrentes;
	}

	public String getUsuarioString() {
		
		return this.usuario.getNome()==null? this.usuario.getLogin(): this.usuario.getNome();
	}
	
	public Orcamento add(Usuario usuario) {
		
		this.usuario = usuario;
		return this;
	}	
	
	@JsonProperty
	public String totalOrcadoString() throws NumberHelpException {

		return NumberHelp.parseDoubleToString(this.totalOrcado());
	}
	
	@JsonProperty
	public String saldoDespesasString() throws NumberHelpException {

		return NumberHelp.parseDoubleToString(this.saldoDespesas());
	}
	
	@JsonProperty
	public String saldoOrcamentarioString() throws NumberHelpException {

		return NumberHelp.parseDoubleToString(this.saldoOrcamentario());
	}
	
	@JsonProperty
	public String getValorReceitaString() throws NumberHelpException {

		return NumberHelp.parseDoubleToString(this.getReceita());
	}

	public Double saldoOrcamentario() {

		return this.totalOrcado() - this.saldoDespesas();
	}
	
	public Double saldoDespesas() {

		Double saldoDespesas = this.debitos.stream().mapToDouble(Debitos::getValor).sum();
		saldoDespesas = saldoDespesas != null ? saldoDespesas : Double.valueOf(ZERO);
		return saldoDespesas;
	}
	
	@JsonIgnore
	public String getJsonValue() throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}

	@Override
	public String converterToString() throws ConverterToStringException {
		String result = "";
		try {
			StringBuffer buffer = new StringBuffer()
					.append("Orcamento")
					.append(SEPARADOR)
					.append(this.mes)
					.append(SEPARADOR)
					.append(this.getValorReceitaString())
					.append(SEPARADOR)
					.append(this.totalOrcadoString())
					.append(SEPARADOR)
					.append(this.saldoDespesasString());
			result = buffer.toString();

		} catch (NumberHelpException e) {
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
		result = prime * result + ((this.mes == null) ? ZERO : mes.hashCode());
		result = prime * result + ((this.basicos == null) ? ZERO : basicos.hashCode());
		result = prime * result + ((this.avulsos == null) ? ZERO : avulsos.hashCode());
		result = prime * result + ((this.recorrentes == null) ? ZERO : recorrentes.hashCode());
		result = prime * result + ((this.receita == null) ? ZERO : receita.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Orcamento)) {
			return false;
		}
		Orcamento other = (Orcamento) obj;
		return super.equals(obj) 
				&& this.id.equals(other.id) 
				&& this.receita.equals(other.receita)
				&& this.avulsos.equals(other.avulsos)
				&& this.recorrentes.equals(other.recorrentes)
				&& this.basicos.equals(other.basicos)
				&& this.mes.equals(other.mes);
	}

}
