package br.com.altamira.material.model;

import java.io.Serializable;

import javax.persistence.*;

/**
 * The primary key class for the OrcMat database table.
 * 
 */
@Embeddable
public class ConversaoUnidadePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	//@Column(name="UNIDADE")
	@OneToOne(/*mappedBy = "codigo",*/ fetch = FetchType.EAGER)
	@JoinColumn(name = "UNIDADE", referencedColumnName = "SIMBOLO")
	private Unidade unidade;

	//@Column(name="COMPONENTE")
	@OneToOne(/*mappedBy = "codigo",*/ fetch = FetchType.EAGER)
	@JoinColumn(name = "CONVERSAO", referencedColumnName = "SIMBOLO")
	private Unidade conversao;

	public ConversaoUnidadePK() {
	}
	
	public ConversaoUnidadePK(Unidade unidade, Unidade conversao) {
		super();
		this.unidade = unidade;
		this.conversao = conversao;
	}

	public Unidade getUnidade() {
		return unidade;
	}


	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}


	public Unidade getConversao() {
		return conversao;
	}


	public void setConversao(Unidade conversao) {
		this.conversao = conversao;
	}


	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		ConversaoUnidadePK castOther = (ConversaoUnidadePK)other;
		return 
			this.unidade.equals(castOther.unidade)
			&& (this.conversao == castOther.conversao);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.unidade.hashCode();
		hash = hash * prime + this.conversao.hashCode();
		
		return hash;
	}
}