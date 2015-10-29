package br.com.altamira.material.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.material.model.MaterialProducao;

@Repository
@Transactional
public interface MaterialProducaoRepository extends JpaRepository<MaterialProducao, Long> {

}
