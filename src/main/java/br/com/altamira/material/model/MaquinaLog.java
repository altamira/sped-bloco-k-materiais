package br.com.altamira.material.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MAQUINA_LOG")
public class MaquinaLog {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name = "MAQUINA")
	private String maquina;
	
	@Column(name = "DATAHORA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date datahora;
	
	@Column(name = "MODO")
	private int modo;
	
	@Column(name = "TEMPO")
	private int tempo;
	
	@Column(name = "OPERADOR")
	private String operador;
	
	@OneToMany(mappedBy = "maquinaLog", fetch = FetchType.LAZY/*, cascade = CascadeType.DETACH*/)
	//@JoinColumn(name = "MAQUINA_LOG", referencedColumnName = "ID")
	private Set<MaquinaLogParametro> parametros;

	public MaquinaLog(String maquina, Date datahora, int modo, int tempo, String operador) {
		super();
		this.maquina = maquina;
		this.datahora = datahora;
		this.modo = modo;
		this.tempo = tempo;
		this.operador = operador;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public int getModo() {
		return modo;
	}

	public void setModo(int modo) {
		this.modo = modo;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	public Set<MaquinaLogParametro> getParametros() {
		return parametros;
	}

	public void setParametros(Set<MaquinaLogParametro> parametros) {
		this.parametros = parametros;
	}
	
}
