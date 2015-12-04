package br.com.altamira.material.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.material.model.MaterialInventario;
import br.com.altamira.material.model.MaterialInventarioPK;

@Repository
@Transactional
public interface MaterialInventarioRepository extends JpaRepository<MaterialInventario, MaterialInventarioPK> {

	MaterialInventario findByIdTipoAndIdNumero(@Param("tipo") String tipo, @Param("numero") long numero);
}
