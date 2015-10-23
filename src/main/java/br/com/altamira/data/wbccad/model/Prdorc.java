package br.com.altamira.data.wbccad.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the prdorc database table.
 * 
 */
public class Prdorc implements Serializable {
	private static final long serialVersionUID = 1L;

	private String produto;

	private Double altura;

	private Timestamp atualizadoem;

	private String codigointegracao;

	private Integer comprimento;

	private String cor_padrao;

	private String descricao;

	private String familia;

	private Timestamp importadoem;

	private Integer importarestrutura;

	private Integer indice;

	private Boolean itemfaturado;

	private Integer largura;

	private String origem;

	private Double peso;

	private String prdcrtadc;

	private String prddscorc;

	private Boolean prdorcExpFlagAlt;

	private Boolean prdorcExpFlagCmp;

	private Boolean prdorcExpFlagLrg;

	private String prdorcFornecedor;

	private String prdorcGrupocor;

	private String prdorcGrupocoresadc;

	private Double prdorcPesoAlt;

	private Double prdorcPesoCmp;

	private Double prdorcPesoFix;

	private Double prdorcPesoPrf;

	private Integer prdorcPrcAltura;

	private Integer prdorcPrcComprimento;

	private Integer prdorcPrcFormula;

	private Integer prdorcPrcLargura;

	private Integer prdorcPrioridade;

	private Boolean prdorcSemcentavos;

	private Double prdOrcAoExportarSubtrairDoComprimento;

	private Integer prdorcchk;

	private String prdOrcImagem;

	private String prdorcvariaveis;

	private Double preco;

	private String situacao;

	private Boolean travarRepresentante;

	private String unidade;

	private Boolean utilizarMedidasOriginais;

	private List<Prdest> prdest = new ArrayList<Prdest>();
	
	public Prdorc() {
	}

	public Double getAltura() {
		return this.altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public Timestamp getAtualizadoem() {
		return this.atualizadoem;
	}

	public void setAtualizadoem(Timestamp atualizadoem) {
		this.atualizadoem = atualizadoem;
	}

	public String getcodigoIntegracao() {
		return this.codigointegracao;
	}

	public void setcodigoIntegracao(String codigointegracao) {
		this.codigointegracao = codigointegracao;
	}

	public Integer getComprimento() {
		return this.comprimento;
	}

	public void setComprimento(Integer comprimento) {
		this.comprimento = comprimento;
	}

	public String getCor_padrao() {
		return this.cor_padrao;
	}

	public void setCor_padrao(String cor_padrao) {
		this.cor_padrao = cor_padrao;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getFamilia() {
		return this.familia;
	}

	public void setFamilia(String familia) {
		this.familia = familia;
	}

	public Timestamp getImportadoem() {
		return this.importadoem;
	}

	public void setImportadoem(Timestamp importadoem) {
		this.importadoem = importadoem;
	}

	public Integer getImportarestrutura() {
		return this.importarestrutura;
	}

	public void setImportarestrutura(Integer importarestrutura) {
		this.importarestrutura = importarestrutura;
	}

	public Integer getIndice() {
		return this.indice;
	}

	public void setIndice(Integer indice) {
		this.indice = indice;
	}

	public Boolean getItemfaturado() {
		return this.itemfaturado;
	}

	public void setItemfaturado(Boolean itemfaturado) {
		this.itemfaturado = itemfaturado;
	}

	public Integer getLargura() {
		return this.largura;
	}

	public void setLargura(Integer largura) {
		this.largura = largura;
	}

	public String getOrigem() {
		return this.origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public Double getPeso() {
		return this.peso;
	}

	public void setPeso(Double peso) {
		this.peso = peso;
	}

	public String getPrdcrtadc() {
		return this.prdcrtadc;
	}

	public void setPrdcrtadc(String prdcrtadc) {
		this.prdcrtadc = prdcrtadc;
	}

	public String getPrddscorc() {
		return this.prddscorc;
	}

	public void setPrddscorc(String prddscorc) {
		this.prddscorc = prddscorc;
	}

	public Boolean getPrdorcExpFlagAlt() {
		return this.prdorcExpFlagAlt;
	}

	public void setPrdorcExpFlagAlt(Boolean prdorcExpFlagAlt) {
		this.prdorcExpFlagAlt = prdorcExpFlagAlt;
	}

	public Boolean getPrdorcExpFlagCmp() {
		return this.prdorcExpFlagCmp;
	}

	public void setPrdorcExpFlagCmp(Boolean prdorcExpFlagCmp) {
		this.prdorcExpFlagCmp = prdorcExpFlagCmp;
	}

	public Boolean getPrdorcExpFlagLrg() {
		return this.prdorcExpFlagLrg;
	}

	public void setPrdorcExpFlagLrg(Boolean prdorcExpFlagLrg) {
		this.prdorcExpFlagLrg = prdorcExpFlagLrg;
	}

	public String getPrdorcFornecedor() {
		return this.prdorcFornecedor;
	}

	public void setPrdorcFornecedor(String prdorcFornecedor) {
		this.prdorcFornecedor = prdorcFornecedor;
	}

	public String getPrdorcGrupocor() {
		return this.prdorcGrupocor;
	}

	public void setPrdorcGrupocor(String prdorcGrupocor) {
		this.prdorcGrupocor = prdorcGrupocor;
	}

	public String getPrdorcGrupocoresadc() {
		return this.prdorcGrupocoresadc;
	}

	public void setPrdorcGrupocoresadc(String prdorcGrupocoresadc) {
		this.prdorcGrupocoresadc = prdorcGrupocoresadc;
	}

	public Double getPrdorcPesoAlt() {
		return this.prdorcPesoAlt;
	}

	public void setPrdorcPesoAlt(Double prdorcPesoAlt) {
		this.prdorcPesoAlt = prdorcPesoAlt;
	}

	public Double getPrdorcPesoCmp() {
		return this.prdorcPesoCmp;
	}

	public void setPrdorcPesoCmp(Double prdorcPesoCmp) {
		this.prdorcPesoCmp = prdorcPesoCmp;
	}

	public Double getPrdorcPesoFix() {
		return this.prdorcPesoFix;
	}

	public void setPrdorcPesoFix(Double prdorcPesoFix) {
		this.prdorcPesoFix = prdorcPesoFix;
	}

	public Double getPrdorcPesoPrf() {
		return this.prdorcPesoPrf;
	}

	public void setPrdorcPesoPrf(Double prdorcPesoPrf) {
		this.prdorcPesoPrf = prdorcPesoPrf;
	}

	public Integer getPrdorcPrcAltura() {
		return this.prdorcPrcAltura;
	}

	public void setPrdorcPrcAltura(Integer prdorcPrcAltura) {
		this.prdorcPrcAltura = prdorcPrcAltura;
	}

	public Integer getPrdorcPrcComprimento() {
		return this.prdorcPrcComprimento;
	}

	public void setPrdorcPrcComprimento(Integer prdorcPrcComprimento) {
		this.prdorcPrcComprimento = prdorcPrcComprimento;
	}

	public Integer getPrdorcPrcFormula() {
		return this.prdorcPrcFormula;
	}

	public void setPrdorcPrcFormula(Integer prdorcPrcFormula) {
		this.prdorcPrcFormula = prdorcPrcFormula;
	}

	public Integer getPrdorcPrcLargura() {
		return this.prdorcPrcLargura;
	}

	public void setPrdorcPrcLargura(Integer prdorcPrcLargura) {
		this.prdorcPrcLargura = prdorcPrcLargura;
	}

	public Integer getPrdorcPrioridade() {
		return this.prdorcPrioridade;
	}

	public void setPrdorcPrioridade(Integer prdorcPrioridade) {
		this.prdorcPrioridade = prdorcPrioridade;
	}

	public Boolean getPrdorcSemcentavos() {
		return this.prdorcSemcentavos;
	}

	public void setPrdorcSemcentavos(Boolean prdorcSemcentavos) {
		this.prdorcSemcentavos = prdorcSemcentavos;
	}

	public Double getPrdOrcAoExportarSubtrairDoComprimento() {
		return this.prdOrcAoExportarSubtrairDoComprimento;
	}

	public void setPrdOrcAoExportarSubtrairDoComprimento(Double prdOrcAoExportarSubtrairDoComprimento) {
		this.prdOrcAoExportarSubtrairDoComprimento = prdOrcAoExportarSubtrairDoComprimento;
	}

	public Integer getPrdorcchk() {
		return this.prdorcchk;
	}

	public void setPrdorcchk(Integer prdorcchk) {
		this.prdorcchk = prdorcchk;
	}

	public String getPrdOrcImagem() {
		return this.prdOrcImagem;
	}

	public void setPrdOrcImagem(String prdOrcImagem) {
		this.prdOrcImagem = prdOrcImagem;
	}

	public String getPrdorcvariaveis() {
		return this.prdorcvariaveis;
	}

	public void setPrdorcvariaveis(String prdorcvariaveis) {
		this.prdorcvariaveis = prdorcvariaveis;
	}

	public Double getPreco() {
		return this.preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public String getProduto() {
		return this.produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public String getSituacao() {
		return this.situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Boolean getTravarRepresentante() {
		return this.travarRepresentante;
	}

	public void setTravarRepresentante(Boolean travarRepresentante) {
		this.travarRepresentante = travarRepresentante;
	}

	public String getUnidade() {
		return this.unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public Boolean getUtilizarMedidasOriginais() {
		return this.utilizarMedidasOriginais;
	}

	public void setUtilizarMedidasOriginais(Boolean utilizarMedidasOriginais) {
		this.utilizarMedidasOriginais = utilizarMedidasOriginais;
	}

	public List<Prdest> getPrdest() {
		return prdest;
	}

	public void setPrdest(List<Prdest> prdest) {
		this.prdest = prdest;
	}

}