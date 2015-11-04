package br.com.altamira.material.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the OrcMat database table.
 * 
 */
@Embeddable
public class MaterialComponentePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	//@Column(name="MATERIAL")
	@OneToOne(/*mappedBy = "codigo",*/ fetch = FetchType.EAGER)
	@JoinColumn(name = "MATERIAL", referencedColumnName = "CODIGO")
	private Material material;

	//@Column(name="COMPONENTE")
	@OneToOne(/*mappedBy = "codigo",*/ fetch = FetchType.EAGER)
	@JoinColumn(name = "COMPONENTE", referencedColumnName = "CODIGO")
	private Material componente;

	public MaterialComponentePK() {
	}
	
	public MaterialComponentePK(Material material, Material componente) {
		super();
		this.material = material;
		this.componente = componente;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Material getComponente() {
		return componente;
	}

	public void setComponente(Material componente) {
		this.componente = componente;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MaterialInventarioPK)) {
			return false;
		}
		MaterialComponentePK castOther = (MaterialComponentePK)other;
		return 
			this.material.equals(castOther.material)
			&& (this.componente == castOther.componente);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.material.hashCode();
		hash = hash * prime + this.componente.hashCode();
		
		return hash;
	}
}