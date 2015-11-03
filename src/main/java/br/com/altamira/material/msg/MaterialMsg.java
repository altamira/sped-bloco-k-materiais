package br.com.altamira.material.msg;

import java.util.Set;

public class MaterialMsg {

	private String movimentacao;
	
	private LoteMsg lote;
	
	private String codigo;
	
	private String descricao;
	
	private Boolean emUso;
	
	private String local;
	
	private Set<MedidaMsg> medidas;

	public String getMovimentacao() {
		return movimentacao;
	}

	public void setMovimentacao(String movimentacao) {
		this.movimentacao = movimentacao;
	}

	public LoteMsg getLote() {
		return lote;
	}

	public void setLote(LoteMsg lote) {
		this.lote = lote;
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

	public Boolean getEmUso() {
		return emUso;
	}

	public void setEmUso(Boolean emUso) {
		this.emUso = emUso;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Set<MedidaMsg> getMedidas() {
		return medidas;
	}

	public void setMedidas(Set<MedidaMsg> medidas) {
		this.medidas = medidas;
	}

}
