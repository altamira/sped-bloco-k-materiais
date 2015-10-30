package br.com.altamira.material.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MATERIAL_MOVIMENTO_ITEM")
public class MaterialMovimentoItem {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "ID")
	private long id;
	
	@Column(name = "MOVIMENTO")
	private Long movimento;
	
	@Column(name = "MOVIMENTO_TIPO")
	private String movimentoTipo;
	
	@Column(name = "MATERIAL")
	private String material;
	
	@Column(name = "LOTE_TIPO")
	private String loteTipo;
	
	@Column(name = "LOTE_NUMERO")
	private Long loteNumero;
	
	@Column(name = "EM_USO")
	private Boolean emUso;
	
	@Column(name = "LOCAL")
	private String local;

	public MaterialMovimentoItem() {
		super();
	}

	public MaterialMovimentoItem(Long movimento, String movimentoTipo,
			String material, String loteTipo, Long loteNumero, Boolean emUso,
			String local) {
		super();
		this.movimento = movimento;
		this.movimentoTipo = movimentoTipo;
		this.material = material;
		this.loteTipo = loteTipo;
		this.loteNumero = loteNumero;
		this.emUso = emUso;
		this.local = local;
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

	public String getMovimentoTipo() {
		return movimentoTipo;
	}

	public void setMovimentoTipo(String movimentoTipo) {
		this.movimentoTipo = movimentoTipo;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getLoteTipo() {
		return loteTipo;
	}

	public void setLoteTipo(String loteTipo) {
		this.loteTipo = loteTipo;
	}

	public Long getLoteNumero() {
		return loteNumero;
	}

	public void setLoteNumero(Long loteNumero) {
		this.loteNumero = loteNumero;
	}

	public Boolean getEmUso() {
		return emUso;
	}

	public void setEmUso(Boolean emUso) {
		this.emUso = emUso;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
	
}
