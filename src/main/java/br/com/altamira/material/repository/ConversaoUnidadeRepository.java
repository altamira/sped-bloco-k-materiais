package br.com.altamira.material.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.material.model.ConversaoUnidade;
import br.com.altamira.material.model.ConversaoUnidadePK;

@Repository
@Transactional
public interface ConversaoUnidadeRepository extends JpaRepository<ConversaoUnidade, ConversaoUnidadePK> {

	ConversaoUnidade findById(ConversaoUnidadePK id);
}
