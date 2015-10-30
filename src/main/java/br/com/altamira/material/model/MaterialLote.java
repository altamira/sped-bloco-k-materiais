package br.com.altamira.material.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "MATERIAL_LOTE")
public class MaterialLote {

	@EmbeddedId
	private MaterialLotePK id;
	
	@Column(name = "MATERIAL")
	private String material;
	
	@OneToMany(/*mappedBy = "id",*/ fetch = FetchType.EAGER)
	@JoinColumns({
	       @JoinColumn(name = "tipo", referencedColumnName = "TIPO"),
	       @JoinColumn(name = "numero", referencedColumnName = "NUMERO")
	    })
	private Set<MaterialLoteMedida> medidas;
		
	@Column(name = "LOCAL")
	private String local;	
	
	public MaterialLote() {
		super();
	}

	public MaterialLote(MaterialLotePK id, String material, String local) {
		super();
		this.id = id;
		this.material = material;
		this.local = local;
	}

	public MaterialLotePK getId() {
		return id;
	}

	public void setId(MaterialLotePK id) {
		this.id = id;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public Set<MaterialLoteMedida> getMedidas() {
		return medidas;
	}

	public void setMedidas(Set<MaterialLoteMedida> medidas) {
		this.medidas = medidas;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
	
}
