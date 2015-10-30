package br.com.altamira.material.msg;

public class OrdemProducaoMsg {

	private String numero;
	
	private String criado;
	
	private int situacao;
	
	private MaterialMsg material;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCriado() {
		return criado;
	}

	public void setCriado(String criado) {
		this.criado = criado;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public MaterialMsg getMaterial() {
		return material;
	}

	public void setMaterial(MaterialMsg material) {
		this.material = material;
	}
	
	
}
