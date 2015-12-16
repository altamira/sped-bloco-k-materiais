package br.com.altamira.material.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "MATERIAL_INVENTARIO")
public class MaterialInventario {

	@EmbeddedId
	private MaterialInventarioPK id;
	
	@Column(name = "MATERIAL")
	private String material;
	
	@OneToMany(/*mappedBy = "id",*/ fetch = FetchType.EAGER)
	@JoinColumns({
	       @JoinColumn(name = "tipo", referencedColumnName = "TIPO"),
	       @JoinColumn(name = "numero", referencedColumnName = "NUMERO")
	    })
	private Set<MaterialInventarioMedida> medidas;
		
	@Column(name = "LOCAL")
	private String local;	
	
	@Column(name = "ULTIMA_ALTERACAO")
	@Temporal(TemporalType.TIMESTAMP)
	private Date alteracao = new Date();
	
	public MaterialInventario() {
		super();
	}

	public MaterialInventario(MaterialInventarioPK id, String material, String local) {
		super();
		this.id = id;
		this.material = material;
		this.local = local;
	}

	public MaterialInventarioPK getId() {
		return id;
	}

	public void setId(MaterialInventarioPK id) {
		this.id = id;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public Set<MaterialInventarioMedida> getMedidas() {
		return medidas;
	}

	public void setMedidas(Set<MaterialInventarioMedida> medidas) {
		this.medidas = medidas;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public Date getAlteracao() {
		return alteracao;
	}

	public void setAlteracao(Date alteracao) {
		this.alteracao = alteracao;
	}
	
}
