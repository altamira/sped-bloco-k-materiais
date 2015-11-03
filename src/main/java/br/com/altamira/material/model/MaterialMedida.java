package br.com.altamira.material.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "MATERIAL_MEDIDA")
public class MaterialMedida {

	@EmbeddedId
	private MaterialMedidaPK id;
	
	@Column(name = "UNIDADE")
	private String unidade;
	
	@Column(name = "FORMULA")
	private String formula;

	@Transient
	private BigDecimal valor;
	
	public MaterialMedida() {
		super();
	}

	public MaterialMedida(MaterialMedidaPK id, String unidade,
			String formula, BigDecimal valor) {
		super();
		this.id = id;
		this.unidade = unidade;
		this.formula = formula;
		this.valor = valor;
	}

	public MaterialMedidaPK getId() {
		return id;
	}

	public void setId(MaterialMedidaPK id) {
		this.id = id;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
		
}
