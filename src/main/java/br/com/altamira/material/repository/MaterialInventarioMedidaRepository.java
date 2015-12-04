package br.com.altamira.material.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.material.model.MaterialInventarioMedida;
import br.com.altamira.material.model.MaterialInventarioMedidaPK;

@Repository
@Transactional
public interface MaterialInventarioMedidaRepository extends JpaRepository<MaterialInventarioMedida, MaterialInventarioMedidaPK> {

	List<MaterialInventarioMedida> findByIdTipoAndIdNumero(@Param("tipo") String tipo, @Param("numero") long numero);
	List<MaterialInventarioMedida> findByIdTipoAndIdNumeroAndIdMedida(@Param("tipo") String tipo, @Param("numero") long numero, @Param("medida") String medida);
}
