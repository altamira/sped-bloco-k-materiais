package br.com.altamira.material.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.material.model.MaterialInventarioMedida;
import br.com.altamira.material.model.MaterialInventarioMedidaPK;

@Repository
@Transactional
public interface MaterialInventarioMedidaRepository extends JpaRepository<MaterialInventarioMedida, MaterialInventarioMedidaPK> {

}
