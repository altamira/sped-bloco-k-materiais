package br.com.altamira.data.wbccad.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * The persistent class for the INTEGRACAO_ORCCAB database table.
 * 
 */
public class IntegracaoOrccab implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idIntegracao_OrcCab;

	private Integer clicod;

	private String clicon;

	private Integer cliconcod;

	private String clinom;

	private Timestamp orcaltdth;

	private BigDecimal orcbas1;

	private BigDecimal orcbas2;

	private BigDecimal orcbas3;

	private String orcnum;

	private BigDecimal orcpercom;

	private String orcpgt;

	private BigDecimal orcvalcom;

	private BigDecimal orcvalemb;

	private BigDecimal orcvalexp;

	private BigDecimal orcvalinv;

	private BigDecimal orcvallst;

	private BigDecimal orcvalluc;

	private BigDecimal orcvalmon;

	private BigDecimal orcvaltrp;

	private BigDecimal orcvalvnd;

	private String pgtcod;

	private Integer przent;

	private String repcod;

	private Integer sitcod;

	private String tipmoncod;

	private List<IntegracaoOrcitm> orcitm;
	
	public IntegracaoOrccab() {
	}

	public Integer getIdIntegracao_OrcCab() {
		return this.idIntegracao_OrcCab;
	}

	public void setIdIntegracao_OrcCab(Integer idIntegracao_OrcCab) {
		this.idIntegracao_OrcCab = idIntegracao_OrcCab;
	}

	public Integer getClicod() {
		return this.clicod;
	}

	public void setClicod(Integer clicod) {
		this.clicod = clicod;
	}

	public String getClicon() {
		return this.clicon;
	}

	public void setClicon(String clicon) {
		this.clicon = clicon;
	}

	public Integer getCliconcod() {
		return this.cliconcod;
	}

	public void setCliconcod(Integer cliconcod) {
		this.cliconcod = cliconcod;
	}

	public String getClinom() {
		return this.clinom;
	}

	public void setClinom(String clinom) {
		this.clinom = clinom;
	}

	public Timestamp getOrcaltdth() {
		return this.orcaltdth;
	}

	public void setOrcaltdth(Timestamp orcaltdth) {
		this.orcaltdth = orcaltdth;
	}

	public BigDecimal getOrcbas1() {
		return this.orcbas1;
	}

	public void setOrcbas1(BigDecimal orcbas1) {
		this.orcbas1 = orcbas1;
	}

	public BigDecimal getOrcbas2() {
		return this.orcbas2;
	}

	public void setOrcbas2(BigDecimal orcbas2) {
		this.orcbas2 = orcbas2;
	}

	public BigDecimal getOrcbas3() {
		return this.orcbas3;
	}

	public void setOrcbas3(BigDecimal orcbas3) {
		this.orcbas3 = orcbas3;
	}

	public String getOrcnum() {
		return this.orcnum;
	}

	public void setOrcnum(String orcnum) {
		this.orcnum = orcnum;
	}

	public BigDecimal getOrcpercom() {
		return this.orcpercom;
	}

	public void setOrcpercom(BigDecimal orcpercom) {
		this.orcpercom = orcpercom;
	}

	public String getOrcpgt() {
		return this.orcpgt;
	}

	public void setOrcpgt(String orcpgt) {
		this.orcpgt = orcpgt;
	}

	public BigDecimal getOrcvalcom() {
		return this.orcvalcom;
	}

	public void setOrcvalcom(BigDecimal orcvalcom) {
		this.orcvalcom = orcvalcom;
	}

	public BigDecimal getOrcvalemb() {
		return this.orcvalemb;
	}

	public void setOrcvalemb(BigDecimal orcvalemb) {
		this.orcvalemb = orcvalemb;
	}

	public BigDecimal getOrcvalexp() {
		return this.orcvalexp;
	}

	public void setOrcvalexp(BigDecimal orcvalexp) {
		this.orcvalexp = orcvalexp;
	}

	public BigDecimal getOrcvalinv() {
		return this.orcvalinv;
	}

	public void setOrcvalinv(BigDecimal orcvalinv) {
		this.orcvalinv = orcvalinv;
	}

	public BigDecimal getOrcvallst() {
		return this.orcvallst;
	}

	public void setOrcvallst(BigDecimal orcvallst) {
		this.orcvallst = orcvallst;
	}

	public BigDecimal getOrcvalluc() {
		return this.orcvalluc;
	}

	public void setOrcvalluc(BigDecimal orcvalluc) {
		this.orcvalluc = orcvalluc;
	}

	public BigDecimal getOrcvalmon() {
		return this.orcvalmon;
	}

	public void setOrcvalmon(BigDecimal orcvalmon) {
		this.orcvalmon = orcvalmon;
	}

	public BigDecimal getOrcvaltrp() {
		return this.orcvaltrp;
	}

	public void setOrcvaltrp(BigDecimal orcvaltrp) {
		this.orcvaltrp = orcvaltrp;
	}

	public BigDecimal getOrcvalvnd() {
		return this.orcvalvnd;
	}

	public void setOrcvalvnd(BigDecimal orcvalvnd) {
		this.orcvalvnd = orcvalvnd;
	}

	public String getPgtcod() {
		return this.pgtcod;
	}

	public void setPgtcod(String pgtcod) {
		this.pgtcod = pgtcod;
	}

	public Integer getPrzent() {
		return this.przent;
	}

	public void setPrzent(Integer przent) {
		this.przent = przent;
	}

	public String getRepcod() {
		return this.repcod;
	}

	public void setRepcod(String repcod) {
		this.repcod = repcod;
	}

	public Integer getSitcod() {
		return this.sitcod;
	}

	public void setSitcod(Integer sitcod) {
		this.sitcod = sitcod;
	}

	public String getTipmoncod() {
		return this.tipmoncod;
	}

	public void setTipmoncod(String tipmoncod) {
		this.tipmoncod = tipmoncod;
	}

	public List<IntegracaoOrcitm> getOrcitm() {
		return orcitm;
	}

	public void setOrcitm(List<IntegracaoOrcitm> orcitm) {
		this.orcitm = orcitm;
	}

}