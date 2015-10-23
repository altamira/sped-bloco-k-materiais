package br.com.altamira.data.wbccad.model;

import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the INTEGRACAO_ORCPRD database table.
 * 
 */
public class IntegracaoOrcprd implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idIntegracao_OrcPrd;

	private String corcod;

	private Integer grpcod;

	private Integer orcitm;

	private String orcnum;

	private Double orcpes;

	private Double orcqtd;

	private Double orctot;

	private String prdcod;

	private String prddsc;

	private Integer subgrpcod;

	private List<IntegracaoOrcprdarv> orcprdarv;
	
	public IntegracaoOrcprd() {
	}

	public Integer getIdIntegracao_OrcPrd() {
		return this.idIntegracao_OrcPrd;
	}

	public void setIdIntegracao_OrcPrd(Integer idIntegracao_OrcPrd) {
		this.idIntegracao_OrcPrd = idIntegracao_OrcPrd;
	}

	public String getCorcod() {
		return this.corcod;
	}

	public void setCorcod(String corcod) {
		this.corcod = corcod;
	}

	public Integer getGrpcod() {
		return this.grpcod;
	}

	public void setGrpcod(Integer grpcod) {
		this.grpcod = grpcod;
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

	public Double getOrcpes() {
		return this.orcpes;
	}

	public void setOrcpes(Double orcpes) {
		this.orcpes = orcpes;
	}

	public Double getOrcqtd() {
		return this.orcqtd;
	}

	public void setOrcqtd(Double orcqtd) {
		this.orcqtd = orcqtd;
	}

	public Double getOrctot() {
		return this.orctot;
	}

	public void setOrctot(Double orctot) {
		this.orctot = orctot;
	}

	public String getPrdcod() {
		return this.prdcod;
	}

	public void setPrdcod(String prdcod) {
		this.prdcod = prdcod;
	}

	public String getPrddsc() {
		return this.prddsc;
	}

	public void setPrddsc(String prddsc) {
		this.prddsc = prddsc;
	}

	public Integer getSubgrpcod() {
		return this.subgrpcod;
	}

	public void setSubgrpcod(Integer subgrpcod) {
		this.subgrpcod = subgrpcod;
	}

	public List<IntegracaoOrcprdarv> getOrcprdarv() {
		return orcprdarv;
	}

	public void setOrcprdarv(List<IntegracaoOrcprdarv> orcprdarv) {
		this.orcprdarv = orcprdarv;
	}

}