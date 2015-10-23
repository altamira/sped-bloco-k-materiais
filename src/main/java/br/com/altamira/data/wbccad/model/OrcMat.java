package br.com.altamira.data.wbccad.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the OrcMat database table.
 * 
 */
public class OrcMat implements Serializable {
	private static final long serialVersionUID = 1L;

	private OrcMatPK id;

	private BigDecimal cofinsfator;

	private BigDecimal comissaoFator;

	private BigDecimal custoCalculo;

	private BigDecimal encargosValor;

	private BigDecimal freteCalculo;

	private BigDecimal freteValor;

	private BigDecimal icmsBase;

	private BigDecimal icmsFator;

	private BigDecimal icmsRedutor;

	private BigDecimal icmsValor;

	private BigDecimal indiceCompra;

	private BigDecimal indiceFaturamento;

	private BigDecimal ipiNaoIncluso;

	private BigDecimal ipiBase;

	private BigDecimal ipiFator;

	private BigDecimal ipiValor;

	private BigDecimal montagemValor;

	private String natureza;

	private String orcmatCodigo;

	private String orcmatCodigoPai;

	private String orcmatCor;

	private String orcmatDescricao;

	private String orcmatFornecedor;

	private String orcmatIdmdescricao;

	private String orcmatIdmunidade;

	private BigDecimal orcmatPeso;

	private Integer orcmatPrcAltura;

	private Integer orcmatPrcComprimento;

	private Integer orcmatPrcFormula;

	private Integer orcmatPrcLargura;

	private String orcmatPrdunidade;

	private BigDecimal orcmatPreco;

	private BigDecimal orcmatPrecoLista;

	private BigDecimal orcmatPrecoListaSem;

	private BigDecimal orcmatPrecoTab;

	private double orcmatQtde;

	private Boolean orcmatSemcentavos;

	private double orcmataltura;

	private String orcmatbase;

	private double orcmatcomprimento;

	private String orcMatCorPesquisa;

	private BigDecimal orcmatdesconto;

	private Integer orcMatGrupo;

	private double orcmatlargura;

	private String orcmatlista;

	private String orcmatsituacao;

	private String orcMatSubGrupo;

	private BigDecimal piscofinsfator;

	private BigDecimal piscofinsvalor;

	private BigDecimal pisfator;

	private Integer prdorcchk;

	private BigDecimal precoFixo;

	private BigDecimal repasseCalculo;

	private BigDecimal royaltiesCalculo;

	private Prdorc prdorc;
	
	private List<OrcDet> orcDet;
	
	public OrcMat() {
	}

	public OrcMatPK getId() {
		return this.id;
	}

	public void setId(OrcMatPK id) {
		this.id = id;
	}

	public BigDecimal getCofinsfator() {
		return this.cofinsfator;
	}

	public void setCofinsfator(BigDecimal cofinsfator) {
		this.cofinsfator = cofinsfator;
	}

	public BigDecimal getComissaoFator() {
		return this.comissaoFator;
	}

	public void setComissaoFator(BigDecimal comissaoFator) {
		this.comissaoFator = comissaoFator;
	}

	public BigDecimal getCustoCalculo() {
		return this.custoCalculo;
	}

	public void setCustoCalculo(BigDecimal custoCalculo) {
		this.custoCalculo = custoCalculo;
	}

	public BigDecimal getEncargosValor() {
		return this.encargosValor;
	}

	public void setEncargosValor(BigDecimal encargosValor) {
		this.encargosValor = encargosValor;
	}

	public BigDecimal getFreteCalculo() {
		return this.freteCalculo;
	}

	public void setFreteCalculo(BigDecimal freteCalculo) {
		this.freteCalculo = freteCalculo;
	}

	public BigDecimal getFreteValor() {
		return this.freteValor;
	}

	public void setFreteValor(BigDecimal freteValor) {
		this.freteValor = freteValor;
	}

	public BigDecimal getIcmsBase() {
		return this.icmsBase;
	}

	public void setIcmsBase(BigDecimal icmsBase) {
		this.icmsBase = icmsBase;
	}

	public BigDecimal getIcmsFator() {
		return this.icmsFator;
	}

	public void setIcmsFator(BigDecimal icmsFator) {
		this.icmsFator = icmsFator;
	}

	public BigDecimal getIcmsRedutor() {
		return this.icmsRedutor;
	}

	public void setIcmsRedutor(BigDecimal icmsRedutor) {
		this.icmsRedutor = icmsRedutor;
	}

	public BigDecimal getIcmsValor() {
		return this.icmsValor;
	}

	public void setIcmsValor(BigDecimal icmsValor) {
		this.icmsValor = icmsValor;
	}

	public BigDecimal getIndiceCompra() {
		return this.indiceCompra;
	}

	public void setIndiceCompra(BigDecimal indiceCompra) {
		this.indiceCompra = indiceCompra;
	}

	public BigDecimal getIndiceFaturamento() {
		return this.indiceFaturamento;
	}

	public void setIndiceFaturamento(BigDecimal indiceFaturamento) {
		this.indiceFaturamento = indiceFaturamento;
	}

	public BigDecimal getIpiNaoIncluso() {
		return this.ipiNaoIncluso;
	}

	public void setIpiNaoIncluso(BigDecimal ipiNaoIncluso) {
		this.ipiNaoIncluso = ipiNaoIncluso;
	}

	public BigDecimal getIpiBase() {
		return this.ipiBase;
	}

	public void setIpiBase(BigDecimal ipiBase) {
		this.ipiBase = ipiBase;
	}

	public BigDecimal getIpiFator() {
		return this.ipiFator;
	}

	public void setIpiFator(BigDecimal ipiFator) {
		this.ipiFator = ipiFator;
	}

	public BigDecimal getIpiValor() {
		return this.ipiValor;
	}

	public void setIpiValor(BigDecimal ipiValor) {
		this.ipiValor = ipiValor;
	}

	public BigDecimal getMontagemValor() {
		return this.montagemValor;
	}

	public void setMontagemValor(BigDecimal montagemValor) {
		this.montagemValor = montagemValor;
	}

	public String getNatureza() {
		return this.natureza;
	}

	public void setNatureza(String natureza) {
		this.natureza = natureza;
	}

	public String getOrcmatCodigo() {
		return this.orcmatCodigo;
	}

	public void setOrcmatCodigo(String orcmatCodigo) {
		this.orcmatCodigo = orcmatCodigo;
	}

	public String getOrcmatCodigoPai() {
		return this.orcmatCodigoPai;
	}

	public void setOrcmatCodigoPai(String orcmatCodigoPai) {
		this.orcmatCodigoPai = orcmatCodigoPai;
	}

	public String getOrcmatCor() {
		return this.orcmatCor;
	}

	public void setOrcmatCor(String orcmatCor) {
		this.orcmatCor = orcmatCor;
	}

	public String getOrcmatDescricao() {
		return this.orcmatDescricao;
	}

	public void setOrcmatDescricao(String orcmatDescricao) {
		this.orcmatDescricao = orcmatDescricao;
	}

	public String getOrcmatFornecedor() {
		return this.orcmatFornecedor;
	}

	public void setOrcmatFornecedor(String orcmatFornecedor) {
		this.orcmatFornecedor = orcmatFornecedor;
	}

	public String getOrcmatIdmdescricao() {
		return this.orcmatIdmdescricao;
	}

	public void setOrcmatIdmdescricao(String orcmatIdmdescricao) {
		this.orcmatIdmdescricao = orcmatIdmdescricao;
	}

	public String getOrcmatIdmunidade() {
		return this.orcmatIdmunidade;
	}

	public void setOrcmatIdmunidade(String orcmatIdmunidade) {
		this.orcmatIdmunidade = orcmatIdmunidade;
	}

	public BigDecimal getOrcmatPeso() {
		return this.orcmatPeso;
	}

	public void setOrcmatPeso(BigDecimal orcmatPeso) {
		this.orcmatPeso = orcmatPeso;
	}

	public Integer getOrcmatPrcAltura() {
		return this.orcmatPrcAltura;
	}

	public void setOrcmatPrcAltura(Integer orcmatPrcAltura) {
		this.orcmatPrcAltura = orcmatPrcAltura;
	}

	public Integer getOrcmatPrcComprimento() {
		return this.orcmatPrcComprimento;
	}

	public void setOrcmatPrcComprimento(Integer orcmatPrcComprimento) {
		this.orcmatPrcComprimento = orcmatPrcComprimento;
	}

	public Integer getOrcmatPrcFormula() {
		return this.orcmatPrcFormula;
	}

	public void setOrcmatPrcFormula(Integer orcmatPrcFormula) {
		this.orcmatPrcFormula = orcmatPrcFormula;
	}

	public Integer getOrcmatPrcLargura() {
		return this.orcmatPrcLargura;
	}

	public void setOrcmatPrcLargura(Integer orcmatPrcLargura) {
		this.orcmatPrcLargura = orcmatPrcLargura;
	}

	public String getOrcmatPrdunidade() {
		return this.orcmatPrdunidade;
	}

	public void setOrcmatPrdunidade(String orcmatPrdunidade) {
		this.orcmatPrdunidade = orcmatPrdunidade;
	}

	public BigDecimal getOrcmatPreco() {
		return this.orcmatPreco;
	}

	public void setOrcmatPreco(BigDecimal orcmatPreco) {
		this.orcmatPreco = orcmatPreco;
	}

	public BigDecimal getOrcmatPrecoLista() {
		return this.orcmatPrecoLista;
	}

	public void setOrcmatPrecoLista(BigDecimal orcmatPrecoLista) {
		this.orcmatPrecoLista = orcmatPrecoLista;
	}

	public BigDecimal getOrcmatPrecoListaSem() {
		return this.orcmatPrecoListaSem;
	}

	public void setOrcmatPrecoListaSem(BigDecimal orcmatPrecoListaSem) {
		this.orcmatPrecoListaSem = orcmatPrecoListaSem;
	}

	public BigDecimal getOrcmatPrecoTab() {
		return this.orcmatPrecoTab;
	}

	public void setOrcmatPrecoTab(BigDecimal orcmatPrecoTab) {
		this.orcmatPrecoTab = orcmatPrecoTab;
	}

	public double getOrcmatQtde() {
		return this.orcmatQtde;
	}

	public void setOrcmatQtde(double orcmatQtde) {
		this.orcmatQtde = orcmatQtde;
	}

	public Boolean getOrcmatSemcentavos() {
		return this.orcmatSemcentavos;
	}

	public void setOrcmatSemcentavos(Boolean orcmatSemcentavos) {
		this.orcmatSemcentavos = orcmatSemcentavos;
	}

	public double getOrcmataltura() {
		return this.orcmataltura;
	}

	public void setOrcmataltura(double orcmataltura) {
		this.orcmataltura = orcmataltura;
	}

	public String getOrcmatbase() {
		return this.orcmatbase;
	}

	public void setOrcmatbase(String orcmatbase) {
		this.orcmatbase = orcmatbase;
	}

	public double getOrcmatcomprimento() {
		return this.orcmatcomprimento;
	}

	public void setOrcmatcomprimento(double orcmatcomprimento) {
		this.orcmatcomprimento = orcmatcomprimento;
	}

	public String getOrcMatCorPesquisa() {
		return this.orcMatCorPesquisa;
	}

	public void setOrcMatCorPesquisa(String orcMatCorPesquisa) {
		this.orcMatCorPesquisa = orcMatCorPesquisa;
	}

	public BigDecimal getOrcmatdesconto() {
		return this.orcmatdesconto;
	}

	public void setOrcmatdesconto(BigDecimal orcmatdesconto) {
		this.orcmatdesconto = orcmatdesconto;
	}

	public Integer getOrcMatGrupo() {
		return this.orcMatGrupo;
	}

	public void setOrcMatGrupo(Integer orcMatGrupo) {
		this.orcMatGrupo = orcMatGrupo;
	}

	public double getOrcmatlargura() {
		return this.orcmatlargura;
	}

	public void setOrcmatlargura(double orcmatlargura) {
		this.orcmatlargura = orcmatlargura;
	}

	public String getOrcmatlista() {
		return this.orcmatlista;
	}

	public void setOrcmatlista(String orcmatlista) {
		this.orcmatlista = orcmatlista;
	}

	public String getOrcmatsituacao() {
		return this.orcmatsituacao;
	}

	public void setOrcmatsituacao(String orcmatsituacao) {
		this.orcmatsituacao = orcmatsituacao;
	}

	public String getOrcMatSubGrupo() {
		return this.orcMatSubGrupo;
	}

	public void setOrcMatSubGrupo(String orcMatSubGrupo) {
		this.orcMatSubGrupo = orcMatSubGrupo;
	}

	public BigDecimal getPiscofinsfator() {
		return this.piscofinsfator;
	}

	public void setPiscofinsfator(BigDecimal piscofinsfator) {
		this.piscofinsfator = piscofinsfator;
	}

	public BigDecimal getPiscofinsvalor() {
		return this.piscofinsvalor;
	}

	public void setPiscofinsvalor(BigDecimal piscofinsvalor) {
		this.piscofinsvalor = piscofinsvalor;
	}

	public BigDecimal getPisfator() {
		return this.pisfator;
	}

	public void setPisfator(BigDecimal pisfator) {
		this.pisfator = pisfator;
	}

	public Integer getPrdorcchk() {
		return this.prdorcchk;
	}

	public void setPrdorcchk(Integer prdorcchk) {
		this.prdorcchk = prdorcchk;
	}

	public BigDecimal getPrecoFixo() {
		return this.precoFixo;
	}

	public void setPrecoFixo(BigDecimal precoFixo) {
		this.precoFixo = precoFixo;
	}

	public BigDecimal getRepasseCalculo() {
		return this.repasseCalculo;
	}

	public void setRepasseCalculo(BigDecimal repasseCalculo) {
		this.repasseCalculo = repasseCalculo;
	}

	public BigDecimal getRoyaltiesCalculo() {
		return this.royaltiesCalculo;
	}

	public void setRoyaltiesCalculo(BigDecimal royaltiesCalculo) {
		this.royaltiesCalculo = royaltiesCalculo;
	}

	public Prdorc getPrdorc() {
		return prdorc;
	}

	public void setPrdorc(Prdorc prdorc) {
		this.prdorc = prdorc;
	}

	public List<OrcDet> getOrcDet() {
		return orcDet;
	}

	public void setOrcDet(List<OrcDet> orcDet) {
		this.orcDet = orcDet;
	}

}