package br.com.altamira.material.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "MATERIAL_VARIAVEL")
public class MaterialVariavel {

	@EmbeddedId
	private MaterialVariavelPK id;
	
	@Column(name = "UNIDADE")
	private String unidade;
	
	@Column(name = "VARIAVEL")
	private String variavel;

	@Transient
	private BigDecimal valor;
	
	public MaterialVariavel() {
		super();
	}

	public MaterialVariavel(MaterialVariavelPK id, String unidade,
			String variavel, BigDecimal valor) {
		super();
		this.id = id;
		this.unidade = unidade;
		this.variavel = variavel;
		this.valor = valor;
	}

	public MaterialVariavelPK getId() {
		return id;
	}

	public void setId(MaterialVariavelPK id) {
		this.id = id;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getVariavel() {
		return variavel;
	}

	public void setVariavel(String variavel) {
		this.variavel = variavel;
	}
		
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
