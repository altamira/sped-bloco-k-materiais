package br.com.altamira.data.wbccad.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the INTEGRACAO_ORCPRDARV database table.
 * 
 */
public class IntegracaoOrcprdarv implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idIntegracao_OrcPrdArv;

	private String corcod;

	private Integer grpcod;

	private Integer orcitm;

	private String orcnum;

	private Double orcpes;

	private Timestamp orcprdarvDth;

	private Integer orcprdarvNivel;

	private Double orcqtd;

	private Double orctot;

	private String prdcod;

	private String prddsc;

	private Integer subgrpcod;

	private List<IntegracaoOrcprdarv> orcprdarv;
	
	public IntegracaoOrcprdarv() {
	}

	public Integer getIdIntegracao_OrcPrdArv() {
		return this.idIntegracao_OrcPrdArv;
	}

	public void setIdIntegracao_OrcPrdArv(Integer idIntegracao_OrcPrdArv) {
		this.idIntegracao_OrcPrdArv = idIntegracao_OrcPrdArv;
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

	public Timestamp getOrcprdarvDth() {
		return this.orcprdarvDth;
	}

	public void setOrcprdarvDth(Timestamp orcprdarvDth) {
		this.orcprdarvDth = orcprdarvDth;
	}

	public Integer getOrcprdarvNivel() {
		return this.orcprdarvNivel;
	}

	public void setOrcprdarvNivel(Integer orcprdarvNivel) {
		this.orcprdarvNivel = orcprdarvNivel;
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