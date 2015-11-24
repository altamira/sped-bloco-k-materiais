package br.com.altamira.material.msg;

import java.util.Date;
import java.util.List;

public class MovimentoMsg {

	private String ihm;
	
	private Date datahora;
	
	private String operador;
	
	private OrdemProducaoMsg ordem;
	
	private List<MaterialMsg> materiais;

	public String getIHM() {
		return ihm;
	}

	public void setIHM(String ihm) {
		this.ihm = ihm;
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

	public OrdemProducaoMsg getOrdem() {
		return ordem;
	}

	public void setOrdem(OrdemProducaoMsg ordem) {
		this.ordem = ordem;
	}

	public List<MaterialMsg> getMateriais() {
		return materiais;
	}

	public void setMateriais(List<MaterialMsg> materiais) {
		this.materiais = materiais;
	}
	
}
