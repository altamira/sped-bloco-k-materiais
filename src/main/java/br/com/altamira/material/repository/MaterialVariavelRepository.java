package br.com.altamira.material.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.material.model.MaterialVariavel;
import br.com.altamira.material.model.MaterialVariavelPK;

@Repository
@Transactional
public interface MaterialVariavelRepository extends JpaRepository<MaterialVariavel, MaterialVariavelPK> {

	MaterialVariavel findByIdMaterial(String codigo);
	
	MaterialVariavel findByIdMaterialCodigoAndIdMedidaNome(String material, String medida);
}
