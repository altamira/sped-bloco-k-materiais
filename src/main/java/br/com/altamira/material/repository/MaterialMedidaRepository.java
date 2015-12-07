package br.com.altamira.material.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.material.model.MaterialMedida;
import br.com.altamira.material.model.MaterialMedidaPK;

@Repository
@Transactional
public interface MaterialMedidaRepository extends JpaRepository<MaterialMedida, MaterialMedidaPK> {

	List<MaterialMedida> findByIdMaterialCodigo(String codigo);
	
	MaterialMedida findByIdMaterialCodigoAndIdMedidaNome(String material, String medida);
}
