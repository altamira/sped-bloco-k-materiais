package br.com.altamira.data.wbccad.model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the INTEGRACAO_ORCSIT database table.
 * 
 */
public class IntegracaoOrcsit implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idIntegracao_OrcSit;

	private Timestamp orcaltdth;

	private String orcnum;

	@Column(name="SITCOD")
	private Integer sitcod;

	public IntegracaoOrcsit() {
	}

	public Integer getIdIntegracao_OrcSit() {
		return this.idIntegracao_OrcSit;
	}

	public void setIdIntegracao_OrcSit(Integer idIntegracao_OrcSit) {
		this.idIntegracao_OrcSit = idIntegracao_OrcSit;
	}

	public Timestamp getOrcaltdth() {
		return this.orcaltdth;
	}

	public void setOrcaltdth(Timestamp orcaltdth) {
		this.orcaltdth = orcaltdth;
	}

	public String getOrcnum() {
		return this.orcnum;
	}

	public void setOrcnum(String orcnum) {
		this.orcnum = orcnum;
	}

	public Integer getSitcod() {
		return this.sitcod;
	}

	public void setSitcod(Integer sitcod) {
		this.sitcod = sitcod;
	}

}