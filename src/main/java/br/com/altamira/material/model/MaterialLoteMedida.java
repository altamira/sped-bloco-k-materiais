package br.com.altamira.material.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MATERIAL_LOTE_MEDIDA")
public class MaterialLoteMedida {
	
	@EmbeddedId
	private MaterialLoteMedidaPK id;
	
	@Column(name = "UNIDADE")
	private String unidade;
	
	@Column(name = "VALOR")
	private BigDecimal valor;

	public MaterialLoteMedida() {
		super();
	}

	public MaterialLoteMedida(MaterialLoteMedidaPK id, String unidade,
			BigDecimal valor) {
		super();
		this.id = id;
		this.unidade = unidade;
		this.valor = valor;
	}

	public MaterialLoteMedidaPK getId() {
		return id;
	}

	public void setId(MaterialLoteMedidaPK id) {
		this.id = id;
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
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other instanceof Medida) {
			Medida castOther = (Medida)other;
			return 
				this.id.getMedida().equals(castOther.getCodigo());			
		}
		if (!(other instanceof MaterialLoteMedida)) {
			return false;
		}
		MaterialLoteMedida castOther = (MaterialLoteMedida)other;
		return 
			this.id.getMedida().equals(castOther.getId().getMedida());
	}
}
