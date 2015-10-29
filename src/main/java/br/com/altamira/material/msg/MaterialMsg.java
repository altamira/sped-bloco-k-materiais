package br.com.altamira.material.msg;

import java.util.List;

public class MaterialMsg {

	private RastreamentoMsg rastreamento;
	
	private String codigo;
	
	private String descricao;
	
	private Boolean emUso;
	
	private String local;
	
	private List<MedidaMsg> medida;

	public RastreamentoMsg getRastreamento() {
		return rastreamento;
	}

	public void setRastreamento(RastreamentoMsg rastreamento) {
		this.rastreamento = rastreamento;
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

	public List<MedidaMsg> getMedida() {
		return medida;
	}

	public void setMedida(List<MedidaMsg> medida) {
		this.medida = medida;
	}

}
