package br.com.altamira.material.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the OrcMat database table.
 * 
 */
@Embeddable
public class MaterialMedidaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="MATERIAL")
	private String material;

	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MEDIDA", nullable = false)*/
	@Column(name = "MEDIDA")
	private String medida;

	public MaterialMedidaPK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MaterialLotePK)) {
			return false;
		}
		MaterialMedidaPK castOther = (MaterialMedidaPK)other;
		return 
			this.material.equals(castOther.material)
			&& (this.medida == castOther.medida);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.material.hashCode();
		hash = hash * prime + this.medida.hashCode();
		
		return hash;
	}
}