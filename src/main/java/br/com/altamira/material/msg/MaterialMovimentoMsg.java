package br.com.altamira.material.msg;

import java.util.List;

public class MaterialMovimentoMsg {

	private String maquina;
	
	private String datahora;
	
	private String operador;
	
	private MaterialMsg producao;
	
	private List<MaterialMsg> consumo;
	
	private MaterialMsg perda;

	public String getMaquina() {
		return maquina;
	}

	public void setMaquina(String maquina) {
		this.maquina = maquina;
	}

	public String getDatahora() {
		return datahora;
	}

	public void setDatahora(String datahora) {
		this.datahora = datahora;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public MaterialMsg getProducao() {
		return producao;
	}

	public void setProducao(MaterialMsg producao) {
		this.producao = producao;
	}

	public List<MaterialMsg> getConsumo() {
		return consumo;
	}

	public void setConsumo(List<MaterialMsg> consumo) {
		this.consumo = consumo;
	}

	public MaterialMsg getPerda() {
		return perda;
	}

	public void setPerda(MaterialMsg perda) {
		this.perda = perda;
	}
	
}
