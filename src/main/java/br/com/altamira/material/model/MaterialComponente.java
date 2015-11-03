package br.com.altamira.material.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "MATERIAL_COMPONENTE")
public class MaterialComponente {

	@EmbeddedId
	private MaterialComponentePK id;
	
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

	public Medida getConsumoMedida() {
		return consumoMedida;
	}

	public MaterialComponentePK getId() {
		return id;
	}

	public void setId(MaterialComponentePK id) {
		this.id = id;
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
