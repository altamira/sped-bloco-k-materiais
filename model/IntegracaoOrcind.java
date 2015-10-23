package br.com.altamira.data.wbccad.model;

import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the INTEGRACAO_ORCIND database table.
 * 
 */
public class IntegracaoOrcind implements Serializable {
	private static final long serialVersionUID = 1L;

	private int idIntegracao_OrcInd;

	private String orcnum;

	private BigDecimal orcval;

	private String tipindcod;

	public IntegracaoOrcind() {
	}

	public int getIdIntegracao_OrcInd() {
		return this.idIntegracao_OrcInd;
	}

	public void setIdIntegracao_OrcInd(int idIntegracao_OrcInd) {
		this.idIntegracao_OrcInd = idIntegracao_OrcInd;
	}

	public String getOrcnum() {
		return this.orcnum;
	}

	public void setOrcnum(String orcnum) {
		this.orcnum = orcnum;
	}

	public BigDecimal getOrcval() {
		return this.orcval;
	}

	public void setOrcval(BigDecimal orcval) {
		this.orcval = orcval;
	}

	public String getTipindcod() {
		return this.tipindcod;
	}

	public void setTipindcod(String tipindcod) {
		this.tipindcod = tipindcod;
	}

}