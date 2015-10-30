package br.com.altamira.material.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "MATERIAL_MOVIMENTO_TIPO")
public class MaterialMovimentoTipo {

	@Id
	@Column(name = "CODIGO")
	private String codigo;
	
	@Column(name = "DESCRICAO")
	private String descricao;
	
	@Column(name = "OPERACAO")
	private String operacao;
	
	@Column(name = "BALANCEADO")
	private Boolean balanceado;
	
	@Column(name = "ALTERA_SALDO_MATERIAL")
	private Boolean alteraSaldoMaterial;
	
	@Column(name = "ALTERA_SALDO_LOTE")
	private Boolean alteraSaldoLote;
	
	@Column(name = "GERAR_LOTE")
	private Boolean gerarLote;

	public MaterialMovimentoTipo() {
		super();
	}

	public MaterialMovimentoTipo(String codigo, String descricao,
			String operacao, Boolean balanceado, Boolean alteraSaldoMaterial,
			Boolean alteraSaldoLote, Boolean gerarLote) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
		this.operacao = operacao;
		this.balanceado = balanceado;
		this.alteraSaldoMaterial = alteraSaldoMaterial;
		this.alteraSaldoLote = alteraSaldoLote;
		this.gerarLote = gerarLote;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public Boolean getBalanceado() {
		return balanceado;
	}

	public void setBalanceado(Boolean balanceado) {
		this.balanceado = balanceado;
	}

	public Boolean getAlteraSaldoMaterial() {
		return alteraSaldoMaterial;
	}

	public void setAlteraSaldoMaterial(Boolean alteraSaldoMaterial) {
		this.alteraSaldoMaterial = alteraSaldoMaterial;
	}

	public Boolean getAlteraSaldoLote() {
		return alteraSaldoLote;
	}

	public void setAlteraSaldoLote(Boolean alteraSaldoLote) {
		this.alteraSaldoLote = alteraSaldoLote;
	}

	public Boolean getGerarLote() {
		return gerarLote;
	}

	public void setGerarLote(Boolean gerarLote) {
		this.gerarLote = gerarLote;
	}
	
}
