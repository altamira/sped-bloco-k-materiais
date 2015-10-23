package br.com.altamira.data.wbccad.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the INTEGRACAO_ORCITM database table.
 * 
 */
public class IntegracaoOrcitm implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idIntegracao_OrcItm;

	private Integer grpcod;

	private BigDecimal orcicm;

	private BigDecimal orcipi;

	private Integer orcitm;

	private String orcnum;

	private String orcprdcod;

	private BigDecimal orcprdqtd;

	private String orctxt;

	private BigDecimal orcval;

	private Integer subgrpcod;

	private List<IntegracaoOrcprd> orcprd;
	
	public IntegracaoOrcitm() {
	}

	public Integer getIdIntegracao_OrcItm() {
		return this.idIntegracao_OrcItm;
	}

	public void setIdIntegracao_OrcItm(Integer idIntegracao_OrcItm) {
		this.idIntegracao_OrcItm = idIntegracao_OrcItm;
	}

	public Integer getGrpcod() {
		return this.grpcod;
	}

	public void setGrpcod(Integer grpcod) {
		this.grpcod = grpcod;
	}

	public BigDecimal getOrcicm() {
		return this.orcicm;
	}

	public void setOrcicm(BigDecimal orcicm) {
		this.orcicm = orcicm;
	}

	public BigDecimal getOrcipi() {
		return this.orcipi;
	}

	public void setOrcipi(BigDecimal orcipi) {
		this.orcipi = orcipi;
	}

	public Integer getOrcitm() {
		return this.orcitm;
	}

	public void setOrcitm(Integer orcitm) {
		this.orcitm = orcitm;
	}

	public String getOrcnum() {
		return this.orcnum;
	}

	public void setOrcnum(String orcnum) {
		this.orcnum = orcnum;
	}

	public String getOrcprdcod() {
		return this.orcprdcod;
	}

	public void setOrcprdcod(String orcprdcod) {
		this.orcprdcod = orcprdcod;
	}

	public BigDecimal getOrcprdqtd() {
		return this.orcprdqtd;
	}

	public void setOrcprdqtd(BigDecimal orcprdqtd) {
		this.orcprdqtd = orcprdqtd;
	}

	public String getOrctxt() {
		return this.orctxt;
	}

	public void setOrctxt(String orctxt) {
		this.orctxt = orctxt;
	}

	public BigDecimal getOrcval() {
		return this.orcval;
	}

	public void setOrcval(BigDecimal orcval) {
		this.orcval = orcval;
	}

	public Integer getSubgrpcod() {
		return this.subgrpcod;
	}

	public void setSubgrpcod(Integer subgrpcod) {
		this.subgrpcod = subgrpcod;
	}

	public List<IntegracaoOrcprd> getOrcprd() {
		return orcprd;
	}

	public void setOrcprd(List<IntegracaoOrcprd> orcprd) {
		this.orcprd = orcprd;
	}

}