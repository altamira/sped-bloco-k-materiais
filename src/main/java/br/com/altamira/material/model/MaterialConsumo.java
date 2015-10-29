package br.com.altamira.material.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MATERIAL_CONSUMO")
public class MaterialConsumo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private long id;
	
	@Column(name = "MOVIMENTO")
	private Long movimento;
	
	@Column(name = "MATERIAL")
	private String material;
	
	@Column(name = "LOTE")
	private String lote;
	
	@Column(name = "MEDIDA")
	private String medida;
	
	@Column(name = "UNIDADE")
	private String unidade;
	
	@Column(name = "VALOR")
	private BigDecimal valor;	
	
	public MaterialConsumo(long movimento, String material, String lote,
			String medida, String unidade, BigDecimal valor) {
		super();
		this.movimento = movimento;
		this.material = material;
		this.lote = lote;
		this.medida = medida;
		this.unidade = unidade;
		this.valor = valor;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getMovimento() {
		return movimento;
	}

	public void setMovimento(Long movimento) {
		this.movimento = movimento;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getMedida() {
		return medida;
	}

	public void setMedida(String medida) {
		this.medida = medida;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
}