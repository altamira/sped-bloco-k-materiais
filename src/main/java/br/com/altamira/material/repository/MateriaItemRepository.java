package br.com.altamira.material.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.material.model.MaterialItem;

@Repository
@Transactional
public interface MateriaItemRepository extends JpaRepository<MaterialItem, String> {

	List<MaterialItem> findAllByMaterialCodigo(String codigo);
}
