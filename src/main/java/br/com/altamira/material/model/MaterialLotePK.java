package br.com.altamira.material.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the OrcMat database table.
 * 
 */
@Embeddable
public class MaterialLotePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="TIPO")
	private String tipo;

	@Column(name="NUMERO")
	private long numero;

	public MaterialLotePK() {
	}
	
	public MaterialLotePK(String tipo, long numero) {
		super();
		this.tipo = tipo;
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public long getNumero() {
		return numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MaterialLotePK)) {
			return false;
		}
		MaterialLotePK castOther = (MaterialLotePK)other;
		return 
			this.tipo.equals(castOther.tipo)
			&& (this.numero == castOther.numero);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.tipo.hashCode();
		hash = (int)(hash * prime + this.numero);
		
		return hash;
	}
}