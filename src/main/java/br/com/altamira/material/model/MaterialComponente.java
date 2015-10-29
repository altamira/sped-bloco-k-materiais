package br.com.altamira.material.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "MATERIAL_COMPONENTE")
public class MaterialComponente {

	@Id
	@GeneratedValue
	@Column(name = "ID")
	private Long id;
	
	@Column(name = "OPERACAO")
	private String operacao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "MATERIAL", nullable = false)
	private Material material;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "COMPONENTE", nullable = false)
	private Material componente;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CONSUMO_MED", nullable = false)
	private Medida consumoMedida;
	
	@Column(name = "CONSUMO_UN")
	private String consumoUnidade;
	
	@Column(name = "CONSUMO_VLR")
	private String consumoExpressao;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PERDA_MED", nullable = false)
	private Medida perdaMedida;
	
	@Column(name = "PERDA_UN")
	private String perdaUnidade;
	
	@Column(name = "PERDA_VLR")
	private String perdaExpressao;
	
	@Transient
	private Double consumoValor;

	@Transient
	private Double perdaValor;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	public Material getComponente() {
		return componente;
	}

	public void setComponente(Material componente) {
		this.componente = componente;
	}

	public Medida getConsumoMedida() {
		return consumoMedida;
	}

	public void setConsumoMedida(Medida consumoMedida) {
		this.consumoMedida = consumoMedida;
	}

	public String getConsumoUnidade() {
		return consumoUnidade;
	}

	public void setConsumoUnidade(String consumoUnidade) {
		this.consumoUnidade = consumoUnidade;
	}

	public String getConsumoExpressao() {
		return consumoExpressao;
	}

	public void setConsumoExpressao(String consumoExpressao) {
		this.consumoExpressao = consumoExpressao;
	}

	public Medida getPerdaMedida() {
		return perdaMedida;
	}

	public void setPerdaMedida(Medida perdaMedida) {
		this.perdaMedida = perdaMedida;
	}

	public String getPerdaUnidade() {
		return perdaUnidade;
	}

	public void setPerdaUnidade(String perdaUnidade) {
		this.perdaUnidade = perdaUnidade;
	}

	public String getPerdaExpressao() {
		return perdaExpressao;
	}

	public void setPerdaExpressao(String perdaExpressao) {
		this.perdaExpressao = perdaExpressao;
	}

	public Double getConsumoValor() {
		return consumoValor;
	}

	public void setConsumoValor(Double consumoValor) {
		this.consumoValor = consumoValor;
	}

	public Double getPerdaValor() {
		return perdaValor;
	}

	public void setPerdaValor(Double perdaValor) {
		this.perdaValor = perdaValor;
	}

}
