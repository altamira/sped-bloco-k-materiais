package br.com.altamira.material.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.material.model.Unidade;

@Repository
@Transactional
public interface UnidadeRepository extends JpaRepository<Unidade, String> {

	Unidade findBySimbolo(String simbolo);
	Unidade findByNome(String nome);
}
