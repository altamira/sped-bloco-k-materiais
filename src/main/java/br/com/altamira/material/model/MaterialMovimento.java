package br.com.altamira.material.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MATERIAL_MOVIMENTO")
public class MaterialMovimento {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private long id;
	
	@Column(name = "DATAHORA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date datahora = new Date();
	
	@Column(name = "MAQUINA")
	private String maquina;
	
	@Column(name = "OPERADOR")
	private String operador;
	
	@OneToMany(mappedBy = "movimento", fetch = FetchType.EAGER)
	private List<MaterialMovimentoItem> materiais;

	public MaterialMovimento(Date datahora, String maquina, String operador) {
		super();
		this.datahora = datahora;
		this.maquina = maquina;
		this.operador = operador;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDatahora() {
		return datahora;
	}

	public void setDatahora(Date datahora) {
		this.datahora = datahora;
	}

	public String getMaquina() {
		return maquina;
	}

	public void setMaquina(String maquina) {
		this.maquina = maquina;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public List<MaterialMovimentoItem> getMateriais() {
		return materiais;
	}

	public void setMateriais(List<MaterialMovimentoItem> materiais) {
		this.materiais = materiais;
	}
	
}
