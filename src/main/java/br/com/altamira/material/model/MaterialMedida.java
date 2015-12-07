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
	
	@Column(name = "CONSUMO")
	private String consumo;
	
	@Column(name = "PERDA")
	private String perda;
	
	@Column(name = "SALDO")
	private Boolean saldo;

	@Transient
	private BigDecimal valor;
	
	public MaterialMedida() {
		super();
	}

	public MaterialMedida(
			MaterialMedidaPK id, 
			String unidade,
			String consumo, 
			BigDecimal valor) {
		
		super();
		this.id = id;
		this.unidade = unidade;
		this.consumo = consumo;
		this.valor = valor;
		
	}

	public MaterialMedida(
			MaterialMedidaPK id, 
			String unidade, 
			String consumo,
			String perda, 
			Boolean saldo, 
			BigDecimal valor) {
		
		super();
		this.id = id;
		this.unidade = unidade;
		this.consumo = consumo;
		this.perda = perda;
		this.saldo = saldo;
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

	public String getConsumo() {
		return consumo;
	}

	public void setConsumo(String consumo) {
		this.consumo = consumo;
	}
	
	public String getPerda() {
		return perda;
	}

	public void setPerda(String perda) {
		this.perda = perda;
	}

	public Boolean getSaldo() {
		return saldo;
	}

	public void setSaldo(Boolean saldo) {
		this.saldo = saldo;
	}
		
	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}
