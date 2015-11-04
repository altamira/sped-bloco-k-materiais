package br.com.altamira.material.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CONVERSAO_UNIDADE")
public class ConversaoUnidade {

	@EmbeddedId
	private ConversaoUnidadePK id;
	
	@Column(name = "FATOR")
	private BigDecimal fator;

	public ConversaoUnidade() {
		super();
	}

	public ConversaoUnidade(ConversaoUnidadePK id, BigDecimal fator) {
		super();
		this.id = id;
		this.fator = fator;
	}

	public ConversaoUnidadePK getId() {
		return id;
	}

	public void setId(ConversaoUnidadePK id) {
		this.id = id;
	}

	public BigDecimal getFator() {
		return fator;
	}

	public void setFator(BigDecimal fator) {
		this.fator = fator;
	}
	
}
