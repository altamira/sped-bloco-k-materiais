package br.com.altamira.material.model;

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
	
	@Column(name = "VALOR")
	private String expressao;

	@Transient
	private Double valor;
	
	public MaterialMedida() {
		super();
	}

	public MaterialMedida(MaterialMedidaPK id, String unidade,
			String expressao, Double valor) {
		super();
		this.id = id;
		this.unidade = unidade;
		this.expressao = expressao;
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

	public String getExpressao() {
		return expressao;
	}

	public void setExpressao(String expressao) {
		this.expressao = expressao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
		
}
