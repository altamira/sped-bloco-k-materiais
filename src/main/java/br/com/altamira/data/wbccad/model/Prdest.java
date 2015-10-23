package br.com.altamira.data.wbccad.model;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the PRDEST database table.
 * 
 */
public class Prdest implements Serializable {
	private static final long serialVersionUID = 1L;

	private PrdestPK id;
	
	private String altura;

	private Timestamp atualizadoem;

	private String comprimento;

	private Boolean fantasma;

	private String formulapreco;

	private Timestamp importadoem;

	private String prdestFormulaAltura;

	private String prdestFormulaComprimento;

	private String prdestFormulaMultiplicar;

	private String prdestFormulaProfundidade;

	private String prdestPeso;

	private String prdestPesoAltura;

	private String prdestPesoComprimento;

	private String prdestPesoFixo;

	private String prdestPesoProfundidade;

	private String prdestQtde;

	private String prdestcondicao;

	private String prdestdepara;

	private String prdestformula;

	private Integer prdestsequencial;

	private String profundidade;

	private Prdorc prdorc;
	
	public Prdest() {
	}

	public PrdestPK getId() {
		return id;
	}

	public void setId(PrdestPK id) {
		this.id = id;
	}

	public String getAltura() {
		return this.altura;
	}

	public void setAltura(String altura) {
		this.altura = altura;
	}

	public Timestamp getAtualizadoem() {
		return this.atualizadoem;
	}

	public void setAtualizadoem(Timestamp atualizadoem) {
		this.atualizadoem = atualizadoem;
	}

	public String getComprimento() {
		return this.comprimento;
	}

	public void setComprimento(String comprimento) {
		this.comprimento = comprimento;
	}

	public Boolean getFantasma() {
		return this.fantasma;
	}

	public void setFantasma(Boolean fantasma) {
		this.fantasma = fantasma;
	}

	public String getFormulapreco() {
		return this.formulapreco;
	}

	public void setFormulapreco(String formulapreco) {
		this.formulapreco = formulapreco;
	}

	public Timestamp getImportadoem() {
		return this.importadoem;
	}

	public void setImportadoem(Timestamp importadoem) {
		this.importadoem = importadoem;
	}

	public String getPrdestFormulaAltura() {
		return this.prdestFormulaAltura;
	}

	public void setPrdestFormulaAltura(String prdestFormulaAltura) {
		this.prdestFormulaAltura = prdestFormulaAltura;
	}

	public String getPrdestFormulaComprimento() {
		return this.prdestFormulaComprimento;
	}

	public void setPrdestFormulaComprimento(String prdestFormulaComprimento) {
		this.prdestFormulaComprimento = prdestFormulaComprimento;
	}

	public String getPrdestFormulaMultiplicar() {
		return this.prdestFormulaMultiplicar;
	}

	public void setPrdestFormulaMultiplicar(String prdestFormulaMultiplicar) {
		this.prdestFormulaMultiplicar = prdestFormulaMultiplicar;
	}

	public String getPrdestFormulaProfundidade() {
		return this.prdestFormulaProfundidade;
	}

	public void setPrdestFormulaProfundidade(String prdestFormulaProfundidade) {
		this.prdestFormulaProfundidade = prdestFormulaProfundidade;
	}

	public String getPrdestPeso() {
		return this.prdestPeso;
	}

	public void setPrdestPeso(String prdestPeso) {
		this.prdestPeso = prdestPeso;
	}

	public String getPrdestPesoAltura() {
		return this.prdestPesoAltura;
	}

	public void setPrdestPesoAltura(String prdestPesoAltura) {
		this.prdestPesoAltura = prdestPesoAltura;
	}

	public String getPrdestPesoComprimento() {
		return this.prdestPesoComprimento;
	}

	public void setPrdestPesoComprimento(String prdestPesoComprimento) {
		this.prdestPesoComprimento = prdestPesoComprimento;
	}

	public String getPrdestPesoFixo() {
		return this.prdestPesoFixo;
	}

	public void setPrdestPesoFixo(String prdestPesoFixo) {
		this.prdestPesoFixo = prdestPesoFixo;
	}

	public String getPrdestPesoProfundidade() {
		return this.prdestPesoProfundidade;
	}

	public void setPrdestPesoProfundidade(String prdestPesoProfundidade) {
		this.prdestPesoProfundidade = prdestPesoProfundidade;
	}

	public String getPrdestQtde() {
		return this.prdestQtde;
	}

	public void setPrdestQtde(String prdestQtde) {
		this.prdestQtde = prdestQtde;
	}

	public String getPrdestcondicao() {
		return this.prdestcondicao;
	}

	public void setPrdestcondicao(String prdestcondicao) {
		this.prdestcondicao = prdestcondicao;
	}

	public String getPrdestdepara() {
		return this.prdestdepara;
	}

	public void setPrdestdepara(String prdestdepara) {
		this.prdestdepara = prdestdepara;
	}

	public String getPrdestformula() {
		return this.prdestformula;
	}

	public void setPrdestformula(String prdestformula) {
		this.prdestformula = prdestformula;
	}

	public Integer getPrdestsequencial() {
		return this.prdestsequencial;
	}

	public void setPrdestsequencial(Integer prdestsequencial) {
		this.prdestsequencial = prdestsequencial;
	}

	public String getProfundidade() {
		return this.profundidade;
	}

	public void setProfundidade(String profundidade) {
		this.profundidade = profundidade;
	}

	public Prdorc getPrdorc() {
		return prdorc;
	}

	public void setPrdorc(Prdorc prdorc) {
		this.prdorc = prdorc;
	}

}