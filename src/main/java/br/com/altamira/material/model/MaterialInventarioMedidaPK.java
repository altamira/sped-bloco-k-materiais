package br.com.altamira.material.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the OrcMat database table.
 * 
 */
@Embeddable
public class MaterialInventarioMedidaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="TIPO")
	private String tipo;

	@Column(name="NUMERO")
	private long numero;
	
	/*@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MEDIDA", nullable = false)*/
	@Column(name = "MEDIDA")
	private String medida;

	public MaterialInventarioMedidaPK() {
		super();
	}

	public MaterialInventarioMedidaPK(String tipo, long numero, String medida) {
		super();
		this.tipo = tipo;
		this.numero = numero;
		this.medida = medida;
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
		if (!(other instanceof MaterialInventarioPK)) {
			return false;
		}
		MaterialInventarioMedidaPK castOther = (MaterialInventarioMedidaPK)other;
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