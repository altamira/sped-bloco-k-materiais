package br.com.altamira.material.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MAQUINA")
public class Maquina {

	@Id
	@Column(name = "CODIGO")
	private String codigo;
	
	@Column(name = "NOME")
	private String nome;
	
	@Column(name = "SETOR")
	private String setor;
	
	@Column(name = "SITUACAO")
	private int situacao;
	
	@Column(name = "SEQUENCIA")
	private long sequencia;
	
	@Column(name = "TEMPO")
	private long tempo;
	
	@Column(name = "OPERADOR")
	private String operador;
	
	@Column(name = "ATIVO")
	private Boolean ativo;
	
	@Column(name = "ULTIMA_ATUALIZACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date atualizacao;
	
	public Maquina() {
		super();
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSetor() {
		return setor;
	}

	public void setSetor(String setor) {
		this.setor = setor;
	}

	public int getSituacao() {
		return situacao;
	}

	public void setSituacao(int situacao) {
		this.situacao = situacao;
	}

	public long getSequencia() {
		return sequencia;
	}

	public void setSequencia(long sequencia) {
		this.sequencia = sequencia;
	}

	public long getTempo() {
		return tempo;
	}

	public void setTempo(long tempo) {
		this.tempo = tempo;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Date getAtualizacao() {
		return atualizacao;
	}

	public void setAtualizacao(Date atualizacao) {
		this.atualizacao = atualizacao;
	}
		
}
