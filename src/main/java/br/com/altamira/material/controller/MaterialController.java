package br.com.altamira.material.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.data.wbccad.model.Prdorc;
import br.com.altamira.material.expression.Expression;
import br.com.altamira.material.model.Material;
import br.com.altamira.material.model.MaterialItem;
import br.com.altamira.material.model.Medida;
import br.com.altamira.material.repository.MateriaItemRepository;
import br.com.altamira.material.repository.MaterialRepository;
import br.com.altamira.material.repository.MedidaRepository;

@Controller
public class MaterialController {

	@Autowired
	private MaterialRepository materialRepository;

	@Autowired
	private MateriaItemRepository materialItemRepository;
	
	@Autowired
	private MedidaRepository medidaRepository;

	@Transactional
	@JmsListener(destination = "import-material")
	public void importMaterial(Prdorc prdorc) {
		System.out.println("Recebido " + prdorc.getProduto());

		Material material = new Material();

		material.setCodigo(prdorc.getProduto());
		material.setDescricao(prdorc.getDescricao());
		material.setTipo("01");

		Material stored = materialRepository.saveAndFlush(material);

		System.out.println("Gravado ID " + stored.getCodigo());
	}

	@JmsListener(destination = "material-list")
	public void calculeMaterial(String codigo) {
		Material material = materialRepository.findOne(codigo);

		if (material == null) {
			System.out.println("Codigo nao entrado" + codigo);
			return;
		}

		Map<String, BigDecimal> variaveis = new HashMap<String, BigDecimal>();

		variaveis.put("c", new BigDecimal(505));
		variaveis.put("q", new BigDecimal(80));

		System.out.println(String.format("%s %s", material.getCodigo(),
				replaceVariables(variaveis, material.getDescricao())));
		printMaterial(variaveis, material, " +");

	}

	private void printMaterial(Map<String, BigDecimal> variaveis,
			Material material, String tab) {

		for (MaterialItem item : material.getItem()) {
			item.getItem().getVariavel().putAll(variaveis);
			Expression exp = new Expression(item.getExpressao());
			exp.setVariables(item.getItem().getVariavel());
			item.getItem().getVariavel().put(item.getMedida().getCodigo(), exp.eval());
			System.out.println(String.format("%s %s %s [%.3f %s]", tab, item
					.getItem().getCodigo(),
					replaceVariables(variaveis, item.getItem().getDescricao()),
					item.getItem().getVariavel().get(item.getMedida().getCodigo()), item.getMedida().getUnidade()));
			for (Map.Entry<String, BigDecimal> v : item.getItem().getVariavel().entrySet()) {
				System.out.println(String.format("%s [%s=%.4f]", tab + " =>", v.getKey(), v.getValue()));
			}
			printMaterial(item.getItem().getVariavel(), item.getItem(), tab + "--");
		}
		
	}

	private String replaceVariables(Map<String, BigDecimal> variaveis,
			String texto) {
		for (Map.Entry<String, BigDecimal> e : variaveis.entrySet()) {
			texto = texto.replaceAll("\\#" + e.getKey() + "\\#", e.getValue().toString());
		}
		return texto;
	}

}
