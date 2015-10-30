package br.com.altamira.material.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.material.model.MaterialLoteMedida;
import br.com.altamira.material.model.MaterialLoteMedidaPK;

@Repository
@Transactional
public interface MaterialLoteMedidaRepository extends JpaRepository<MaterialLoteMedida, MaterialLoteMedidaPK> {

}
