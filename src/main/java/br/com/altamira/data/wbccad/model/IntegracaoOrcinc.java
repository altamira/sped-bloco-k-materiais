package br.com.altamira.data.wbccad.model;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the INTEGRACAO_ORCINC database table.
 * 
 */
public class IntegracaoOrcinc implements Serializable {
	private static final long serialVersionUID = 1L;

	private String orcnum;

	private String acadsc;

	private String clicon;

	private String cliema;

	private String cliend;

	private String clifax;

	private String clifon;

	private String climun;

	private String clinom;

	private String estcod;

	private Timestamp orcdat;

	private String repcod;

	private String tipvndcod;

	private String usrcod;

	public IntegracaoOrcinc() {
	}

	public String getOrcnum() {
		return this.orcnum;
	}

	public void setOrcnum(String orcnum) {
		this.orcnum = orcnum;
	}

	public String getAcadsc() {
		return this.acadsc;
	}

	public void setAcadsc(String acadsc) {
		this.acadsc = acadsc;
	}

	public String getClicon() {
		return this.clicon;
	}

	public void setClicon(String clicon) {
		this.clicon = clicon;
	}

	public String getCliema() {
		return this.cliema;
	}

	public void setCliema(String cliema) {
		this.cliema = cliema;
	}

	public String getCliend() {
		return this.cliend;
	}

	public void setCliend(String cliend) {
		this.cliend = cliend;
	}

	public String getClifax() {
		return this.clifax;
	}

	public void setClifax(String clifax) {
		this.clifax = clifax;
	}

	public String getClifon() {
		return this.clifon;
	}

	public void setClifon(String clifon) {
		this.clifon = clifon;
	}

	public String getClimun() {
		return this.climun;
	}

	public void setClimun(String climun) {
		this.climun = climun;
	}

	public String getClinom() {
		return this.clinom;
	}

	public void setClinom(String clinom) {
		this.clinom = clinom;
	}

	public String getEstcod() {
		return this.estcod;
	}

	public void setEstcod(String estcod) {
		this.estcod = estcod;
	}

	public Timestamp getOrcdat() {
		return this.orcdat;
	}

	public void setOrcdat(Timestamp orcdat) {
		this.orcdat = orcdat;
	}

	public String getRepcod() {
		return this.repcod;
	}

	public void setRepcod(String repcod) {
		this.repcod = repcod;
	}

	public String getTipvndcod() {
		return this.tipvndcod;
	}

	public void setTipvndcod(String tipvndcod) {
		this.tipvndcod = tipvndcod;
	}

	public String getUsrcod() {
		return this.usrcod;
	}

	public void setUsrcod(String usrcod) {
		this.usrcod = usrcod;
	}

}