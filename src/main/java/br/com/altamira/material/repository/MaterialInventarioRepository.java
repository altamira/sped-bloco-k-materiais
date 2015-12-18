package br.com.altamira.material.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.material.model.MaterialInventario;
import br.com.altamira.material.model.MaterialInventarioPK;

@Repository
@Transactional
public interface MaterialInventarioRepository extends JpaRepository<MaterialInventario, MaterialInventarioPK>/*, MaterialInventarioRepositoryCustom*/ {

	MaterialInventario findByIdTipoAndIdNumero(@Param("tipo") String tipo, @Param("numero") long numero);

	@Procedure
	List<?> inventario(/*@Param("arg") Integer arg*/);
}
