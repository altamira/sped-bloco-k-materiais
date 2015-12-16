package br.com.altamira.material.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MATERIAL_MOVIMENTO_LOG_ERRO")
public class MaterialMovimentoLogErro {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "DATAHORA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date datahora;
	
	@Column(name = "MOVIMENTO")
	private long movimento;
	
	@Column(name = "ERRO", columnDefinition = "VARCHAR(255)")
	private String erro;
	
	@Column(name = "MENSAGEM", columnDefinition = "CLOB NULL")
	private String mensagem;

	public MaterialMovimentoLogErro() {
		super();
	}

	public MaterialMovimentoLogErro(Date datahora, String erro, String mensagem) {
		super();
		this.datahora = datahora;
		this.erro = erro.substring(0, 255);
		this.mensagem = mensagem;
	}

	public MaterialMovimentoLogErro(Date datahora, long movimento, String erro,
			String mensagem) {
		super();
		this.datahora = datahora;
		this.movimento = movimento;
		this.erro = erro.substring(0, 255);
		this.mensagem = mensagem;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDatahora() {
		return datahora;
	}

	public void setDatahora(Date datahora) {
		this.datahora = datahora;
	}

	public long getMovimento() {
		return movimento;
	}

	public void setMovimento(long movimento) {
		this.movimento = movimento;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro.substring(0, 255);
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}
	
}
