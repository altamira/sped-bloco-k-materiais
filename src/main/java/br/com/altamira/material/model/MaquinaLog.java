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
@Table(name = "MAQUINA_LOG")
public class MaquinaLog {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name = "MAQUINA")
	private String maquina;
	
	@Column(name = "DATAHORA", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date datahora;
	
	@Column(name = "MODO")
	private int modo;
	
	@Column(name = "USUARIO")
	private String usuario;
	
	@Column(name = "TORQUE_MIN")
	private int torqueMin;
	
	@Column(name = "TORQUE_MAX")
	private int torqueMax;
	
	@Column(name = "CORRENTE_MIN")
	private int correnteMin;
	
	@Column(name = "CORRENTE_MAX")
	private int correnteMax;
	
	@Column(name = "TEMP_MIN")
	private int temperaturaMin;
	
	@Column(name = "TEMP_MAX")
	private int temperaturaMax;
	
	@Column(name = "UPTIME")
	private int uptime;

	public MaquinaLog(String maquina, int modo, String usuario, int torqueMin,
			int torqueMax, int correnteMin, int correnteMax,
			int temperaturaMin, int temperaturaMax, int uptime) {
		super();
		this.maquina = maquina;
		this.modo = modo;
		this.usuario = usuario;
		this.torqueMin = torqueMin;
		this.torqueMax = torqueMax;
		this.correnteMin = correnteMin;
		this.correnteMax = correnteMax;
		this.temperaturaMin = temperaturaMin;
		this.temperaturaMax = temperaturaMax;
		this.uptime = uptime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public int getTorqueMin() {
		return torqueMin;
	}

	public void setTorqueMin(int torqueMin) {
		this.torqueMin = torqueMin;
	}

	public int getTorqueMax() {
		return torqueMax;
	}

	public void setTorqueMax(int torqueMax) {
		this.torqueMax = torqueMax;
	}

	public int getCorrenteMin() {
		return correnteMin;
	}

	public void setCorrenteMin(int correnteMin) {
		this.correnteMin = correnteMin;
	}

	public int getCorrenteMax() {
		return correnteMax;
	}

	public void setCorrenteMax(int correnteMax) {
		this.correnteMax = correnteMax;
	}

	public int getTemperaturaMin() {
		return temperaturaMin;
	}

	public void setTemperaturaMin(int temperaturaMin) {
		this.temperaturaMin = temperaturaMin;
	}

	public int getTemperaturaMax() {
		return temperaturaMax;
	}

	public void setTemperaturaMax(int temperaturaMax) {
		this.temperaturaMax = temperaturaMax;
	}

	public int getUptime() {
		return uptime;
	}

	public void setUptime(int uptime) {
		this.uptime = uptime;
	}
	
}
