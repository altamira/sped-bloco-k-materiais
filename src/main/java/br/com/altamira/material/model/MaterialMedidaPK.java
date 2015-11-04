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

	//@Column(name="MATERIAL")
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MATERIAL", referencedColumnName = "CODIGO", nullable = false)
	private Material material;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MEDIDA", referencedColumnName = "NOME", nullable = false)
	//@Column(name = "MEDIDA")
	private Medida medida;

	public MaterialMedidaPK() {
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Medida getMedida() {
		return medida;
	}

	public void setMedida(Medida medida) {
		this.medida = medida;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MaterialInventarioPK)) {
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