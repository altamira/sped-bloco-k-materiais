package br.com.altamira.material.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.material.model.MaterialMedida;
import br.com.altamira.material.model.MaterialMedidaPK;

@Repository
@Transactional
public interface MaterialMedidaRepository extends JpaRepository<MaterialMedida, MaterialMedidaPK> {

	List<MaterialMedida> findByIdMaterialCodigo(String codigo);
	
	MaterialMedida findByIdMaterialCodigoAndIdMedidaNome(String material, String medida);
	
	@Query(value = "INSERT INTO MATERIAL_MEDIDA SELECT :codigo_material_destino, MEDIDA, UNIDADE, CONSUMO, PERDA, SALDO FROM MATERIAL_MEDIDA WHERE MATERIAL = :codigo_material_origem AND NOT EXISTS (SELECT 1 FROM MATERIAL_MEDIDA MM WHERE MM.MATERIAL = :codigo_material_destino AND MM.MEDIDA = MATERIAL_MEDIDA.MEDIDA)", nativeQuery = true)
	List<MaterialMedida> copiarMedidas(String codigo_material_origem, String codigo_material_destino);
}
