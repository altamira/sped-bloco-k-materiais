package br.com.altamira.material.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the OrcMat database table.
 * 
 */
@Embeddable
public class MaquinaLogParametroPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID")
	private Long id;

	@Column(name = "MEDIDA")
	private String medida;

	public MaquinaLogParametroPK() {
	}

	public MaquinaLogParametroPK(Long id, String medida) {
		super();
		this.id = id;
		this.medida = medida;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		MaquinaLogParametroPK castOther = (MaquinaLogParametroPK)other;
		return 
			(this.id == castOther.id)
			&& (this.medida == castOther.medida);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.id.hashCode();
		hash = hash * prime + this.medida.hashCode();
		
		return hash;
	}
}