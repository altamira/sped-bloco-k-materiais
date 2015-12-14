package br.com.altamira.material.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "MATERIAL")
public class Material {

	@Id
	@Column(name = "CODIGO", unique = true, nullable = false)
	private String codigo;
	
	@Column(name = "DESCRICAO", unique = true, nullable = false)
	private String descricao;
	
	@Column(name = "TIPO", nullable = false)
	private String tipo;
	
	@OneToMany(/*mappedBy = "id",*/ fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	//@JoinColumns({
	@JoinColumn(name = "MATERIAL", referencedColumnName = "CODIGO")
	//    })
	private Set<MaterialMedida> medidas;

	@OneToMany(/*mappedBy = "id",*/ fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	//@JoinColumns({
	@JoinColumn(name = "MATERIAL", referencedColumnName = "CODIGO")
	//    })
	private Set<MaterialComponente> componentes;
	
	@Transient
	private Map<String, BigDecimal> variavel = new HashMap<String, BigDecimal>();
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Set<MaterialMedida> getMedidas() {
		return medidas;
	}

	public void setMedidas(Set<MaterialMedida> medidas) {
		this.medidas = medidas;
	}

	public Set<MaterialComponente> getComponentes() {
		return componentes;
	}

	public void setComponentes(Set<MaterialComponente> componentes) {
		this.componentes = componentes;
	}

	public Map<String, BigDecimal> getVariavel() {
		return variavel;
	}

	public void setVariavel(Map<String, BigDecimal> variavel) {
		this.variavel = variavel;
	}
	
}
