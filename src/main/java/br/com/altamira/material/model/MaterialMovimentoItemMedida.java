package br.com.altamira.material.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "MATERIAL_MOVIMENTO_ITEM_MEDIDA")
public class MaterialMovimentoItemMedida {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private long id;
	
	@Column(name = "ITEM")
	private long item;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MEDIDA", nullable = false)
	private Medida medida;
	
	@Column(name = "UNIDADE")
	private String unidade;
	
	@Column(name = "VALOR")
	private BigDecimal valor;

	public MaterialMovimentoItemMedida(long item, Medida medida,
			String unidade, BigDecimal valor) {
		super();
		this.item = item;
		this.medida = medida;
		this.unidade = unidade;
		this.valor = valor;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getItem() {
		return item;
	}

	public void setItem(long item) {
		this.item = item;
	}

	public Medida getMedida() {
		return medida;
	}

	public void setMedida(Medida medida) {
		this.medida = medida;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

}

