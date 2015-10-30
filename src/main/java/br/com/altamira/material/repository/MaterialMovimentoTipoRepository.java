package br.com.altamira.material.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.material.model.MaterialMovimentoTipo;

@Repository
@Transactional
public interface MaterialMovimentoTipoRepository extends JpaRepository<MaterialMovimentoTipo, String> {

}
