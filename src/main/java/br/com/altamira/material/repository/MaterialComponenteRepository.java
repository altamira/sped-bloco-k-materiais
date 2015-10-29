package br.com.altamira.material.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.material.model.MaterialComponente;

@Repository
@Transactional
public interface MaterialComponenteRepository extends JpaRepository<MaterialComponente, String> {

	List<MaterialComponente> findAllByMaterialCodigo(String codigo);
}
