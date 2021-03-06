package br.com.altamira.material.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.material.model.MaterialMovimentoItem;

@Repository
@Transactional
public interface MaterialMovimentoItemRepository extends JpaRepository<MaterialMovimentoItem, Long> {

}
