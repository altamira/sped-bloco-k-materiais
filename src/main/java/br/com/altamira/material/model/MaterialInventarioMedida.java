package br.com.altamira.material.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "MATERIAL_INVENTARIO_MEDIDA")
public class MaterialInventarioMedida {
	
	@EmbeddedId
	private MaterialInventarioMedidaPK id;
	
	@Column(name = "UNIDADE")
	private String unidade;
	
	@Column(name = "VALOR")
	private BigDecimal valor;

	public MaterialInventarioMedida() {
		super();
	}

	public MaterialInventarioMedida(MaterialInventarioMedidaPK id, String unidade,
			BigDecimal valor) {
		super();
		this.id = id;
		this.unidade = unidade;
		this.valor = valor;
	}

	public MaterialInventarioMedidaPK getId() {
		return id;
	}

	public void setId(MaterialInventarioMedidaPK id) {
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
				this.id.getMedida().equals(castOther.getNome());			
		}
		if (!(other instanceof MaterialInventarioMedida)) {
			return false;
		}
		MaterialInventarioMedida castOther = (MaterialInventarioMedida)other;
		return 
			this.id.getMedida().equals(castOther.getId().getMedida());
	}
}
