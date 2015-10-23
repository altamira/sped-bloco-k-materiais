package br.com.altamira.data.wbccad.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the OrcItm database table.
 * 
 */
public class OrcItm implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer idOrcItm;

	private String numeroOrcamento;

	private BigDecimal orcitmAltura;

	private BigDecimal orcitmComprimento;

	private Boolean orcItm_Desc_Destacado;

	private BigDecimal orcItm_Desconto_Grupo;

	private BigDecimal orcItm_Diferenca;

	private String orcitmDtc;

	private BigDecimal orcItm_Encargos;

	private BigDecimal orcItm_Frete;

	private Integer orcitmGrupo;

	private BigDecimal orcItm_IPI;

	private String orcitmItem;

	private BigDecimal orcitmLargura;

	private BigDecimal orcItm_Preco_Lista;

	private BigDecimal orcItm_Preco_Lista_Sem;

	private Double orcitmQtde;

	private String orcitmReferencia;

	private String orcitmSubgrupo;

	private Boolean orcitmSuprimirItens;

	private Integer orcitmTotal;

	private BigDecimal orcItm_ValGrupoComDesc;

	private BigDecimal orcitmValorTotal;

	private String propostaDescricao;

	private Integer propostaGrupo;

	private String propostaImagem;

	private Integer propostaOrdem;

	private String propostaTextoBase;

	private String propostaTextoItem;

	private String orctxt;
	
	private OrcMat orcMat;
	
	private List<OrcDet> orcdet;
	
	public OrcItm() {
	}

	public Integer getIdOrcItm() {
		return this.idOrcItm;
	}

	public void setIdOrcItm(Integer idOrcItm) {
		this.idOrcItm = idOrcItm;
	}

	public String getNumeroOrcamento() {
		return this.numeroOrcamento;
	}

	public void setNumeroOrcamento(String numeroOrcamento) {
		this.numeroOrcamento = numeroOrcamento;
	}

	public BigDecimal getOrcitmAltura() {
		return this.orcitmAltura;
	}

	public void setOrcitmAltura(BigDecimal orcitmAltura) {
		this.orcitmAltura = orcitmAltura;
	}

	public BigDecimal getOrcitmComprimento() {
		return this.orcitmComprimento;
	}

	public void setOrcitmComprimento(BigDecimal orcitmComprimento) {
		this.orcitmComprimento = orcitmComprimento;
	}

	public Boolean getOrcItm_Desc_Destacado() {
		return this.orcItm_Desc_Destacado;
	}

	public void setOrcItm_Desc_Destacado(Boolean orcItm_Desc_Destacado) {
		this.orcItm_Desc_Destacado = orcItm_Desc_Destacado;
	}

	public BigDecimal getOrcItm_Desconto_Grupo() {
		return this.orcItm_Desconto_Grupo;
	}

	public void setOrcItm_Desconto_Grupo(BigDecimal orcItm_Desconto_Grupo) {
		this.orcItm_Desconto_Grupo = orcItm_Desconto_Grupo;
	}

	public BigDecimal getOrcItm_Diferenca() {
		return this.orcItm_Diferenca;
	}

	public void setOrcItm_Diferenca(BigDecimal orcItm_Diferenca) {
		this.orcItm_Diferenca = orcItm_Diferenca;
	}

	public String getOrcitmDtc() {
		return this.orcitmDtc;
	}

	public void setOrcitmDtc(String orcitmDtc) {
		this.orcitmDtc = orcitmDtc;
	}

	public BigDecimal getOrcItm_Encargos() {
		return this.orcItm_Encargos;
	}

	public void setOrcItm_Encargos(BigDecimal orcItm_Encargos) {
		this.orcItm_Encargos = orcItm_Encargos;
	}

	public BigDecimal getOrcItm_Frete() {
		return this.orcItm_Frete;
	}

	public void setOrcItm_Frete(BigDecimal orcItm_Frete) {
		this.orcItm_Frete = orcItm_Frete;
	}

	public Integer getOrcitmGrupo() {
		return this.orcitmGrupo;
	}

	public void setOrcitmGrupo(Integer orcitmGrupo) {
		this.orcitmGrupo = orcitmGrupo;
	}

	public BigDecimal getOrcItm_IPI() {
		return this.orcItm_IPI;
	}

	public void setOrcItm_IPI(BigDecimal orcItm_IPI) {
		this.orcItm_IPI = orcItm_IPI;
	}

	public String getOrcitmItem() {
		return this.orcitmItem;
	}

	public void setOrcitmItem(String orcitmItem) {
		this.orcitmItem = orcitmItem;
	}

	public BigDecimal getOrcitmLargura() {
		return this.orcitmLargura;
	}

	public void setOrcitmLargura(BigDecimal orcitmLargura) {
		this.orcitmLargura = orcitmLargura;
	}

	public BigDecimal getOrcItm_Preco_Lista() {
		return this.orcItm_Preco_Lista;
	}

	public void setOrcItm_Preco_Lista(BigDecimal orcItm_Preco_Lista) {
		this.orcItm_Preco_Lista = orcItm_Preco_Lista;
	}

	public BigDecimal getOrcItm_Preco_Lista_Sem() {
		return this.orcItm_Preco_Lista_Sem;
	}

	public void setOrcItm_Preco_Lista_Sem(BigDecimal orcItm_Preco_Lista_Sem) {
		this.orcItm_Preco_Lista_Sem = orcItm_Preco_Lista_Sem;
	}

	public Double getOrcitmQtde() {
		return this.orcitmQtde;
	}

	public void setOrcitmQtde(Double orcitmQtde) {
		this.orcitmQtde = orcitmQtde;
	}

	public String getOrcitmReferencia() {
		return this.orcitmReferencia;
	}

	public void setOrcitmReferencia(String orcitmReferencia) {
		this.orcitmReferencia = orcitmReferencia;
	}

	public String getOrcitmSubgrupo() {
		return this.orcitmSubgrupo;
	}

	public void setOrcitmSubgrupo(String orcitmSubgrupo) {
		this.orcitmSubgrupo = orcitmSubgrupo;
	}

	public Boolean getOrcitmSuprimirItens() {
		return this.orcitmSuprimirItens;
	}

	public void setOrcitmSuprimirItens(Boolean orcitmSuprimirItens) {
		this.orcitmSuprimirItens = orcitmSuprimirItens;
	}

	public Integer getOrcitmTotal() {
		return this.orcitmTotal;
	}

	public void setOrcitmTotal(Integer orcitmTotal) {
		this.orcitmTotal = orcitmTotal;
	}

	public BigDecimal getOrcItm_ValGrupoComDesc() {
		return this.orcItm_ValGrupoComDesc;
	}

	public void setOrcItm_ValGrupoComDesc(BigDecimal orcItm_ValGrupoComDesc) {
		this.orcItm_ValGrupoComDesc = orcItm_ValGrupoComDesc;
	}

	public BigDecimal getOrcitmValorTotal() {
		return this.orcitmValorTotal;
	}

	public void setOrcitmValorTotal(BigDecimal orcitmValorTotal) {
		this.orcitmValorTotal = orcitmValorTotal;
	}

	public String getPropostaDescricao() {
		return this.propostaDescricao;
	}

	public void setPropostaDescricao(String propostaDescricao) {
		this.propostaDescricao = propostaDescricao;
	}

	public Integer getPropostaGrupo() {
		return this.propostaGrupo;
	}

	public void setPropostaGrupo(Integer propostaGrupo) {
		this.propostaGrupo = propostaGrupo;
	}

	public String getPropostaImagem() {
		return this.propostaImagem;
	}

	public void setPropostaImagem(String propostaImagem) {
		this.propostaImagem = propostaImagem;
	}

	public Integer getPropostaOrdem() {
		return this.propostaOrdem;
	}

	public void setPropostaOrdem(Integer propostaOrdem) {
		this.propostaOrdem = propostaOrdem;
	}

	public String getPropostaTextoBase() {
		return this.propostaTextoBase;
	}

	public void setPropostaTextoBase(String propostaTextoBase) {
		this.propostaTextoBase = propostaTextoBase;
	}

	public String getPropostaTextoItem() {
		return this.propostaTextoItem;
	}

	public void setPropostaTextoItem(String propostaTextoItem) {
		this.propostaTextoItem = propostaTextoItem;
	}

	public String getOrctxt() {
		return orctxt;
	}

	public void setOrctxt(String orctxt) {
		this.orctxt = orctxt;
	}

	public OrcMat getOrcMat() {
		return orcMat;
	}

	public void setOrcMat(OrcMat orcMat) {
		this.orcMat = orcMat;
	}

	public List<OrcDet> getOrcdet() {
		return orcdet;
	}

	public void setOrcdet(List<OrcDet> orcdet) {
		this.orcdet = orcdet;
	}

}