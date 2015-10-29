package br.com.altamira.material.msg;

import java.util.Date;


public class MaterialCadastroMsg {

	private String maquina;
	
	private Date datahora;
	
	private String operador;
	
	private MaterialMsg material;

	public String getMaquina() {
		return maquina;
	}

	public void setMaquina(String maquina) {
		this.maquina = maquina;
	}

	public Date getDatahora() {
		return datahora;
	}

	public void setDatahora(Date datahora) {
		this.datahora = datahora;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public MaterialMsg getMaterial() {
		return material;
	}

	public void setMaterial(MaterialMsg material) {
		this.material = material;
	}
	
}
