package br.com.altamira.material.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MATERIAL_LOTE")
public class MaterialLote {

	@EmbeddedId
	private MaterialLotePK id;
	
	@Column(name = "MATERIAL")
	private String material;
	
	@Column(name = "QUANT_MED")
	private String medida;
	
	@Column(name = "QUANT_UN")
	private String unidade;
	
	@Column(name = "QUANT_VLR")
	private BigDecimal valor;

	public MaterialLote() {
		super();
	}

	public MaterialLote(MaterialLotePK id, String material, String medida,
			String unidade, BigDecimal valor) {
		super();
		this.id = id;
		this.material = material;
		this.medida = medida;
		this.unidade = unidade;
		this.valor = valor;
	}

	public MaterialLotePK getId() {
		return id;
	}

	public void setId(MaterialLotePK id) {
		this.id = id;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getMedida() {
		return medida;
	}

	public void setMedida(String medida) {
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
