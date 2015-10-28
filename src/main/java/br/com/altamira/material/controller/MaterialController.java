package br.com.altamira.material.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.data.wbccad.model.Prdorc;
import br.com.altamira.material.expression.Expression;
import br.com.altamira.material.model.Material;
import br.com.altamira.material.model.MaterialItem;
import br.com.altamira.material.repository.MateriaItemRepository;
import br.com.altamira.material.repository.MaterialRepository;
import br.com.altamira.material.repository.MedidaRepository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MaterialController {

	@Autowired
	private MaterialRepository materialRepository;

	@Autowired
	private MateriaItemRepository materialItemRepository;
	
	@Autowired
	private MedidaRepository medidaRepository;

	@Transactional
	@JmsListener(destination = "IMPORT-MATERIAL")
	public void importaaMaterial(Prdorc prdorc) {
		System.out.println("Recebido " + prdorc.getProduto());

		Material material = new Material();

		material.setCodigo(prdorc.getProduto());
		material.setDescricao(prdorc.getDescricao());
		material.setTipo("01");

		Material stored = materialRepository.saveAndFlush(material);

		System.out.println("Gravado ID " + stored.getCodigo());
	}

	@JmsListener(destination = "MATERIAL-LIST")
	public void calculaEstruturaMaterial(String parameters) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper o = new ObjectMapper();
		
		@SuppressWarnings("unchecked")
		Map<String, Object> param = o.readValue(parameters, Map.class);
		
		Material material = materialRepository.findOne(param.get("codigo").toString());

		if (material == null) {
			System.out.println("Codigo nao entrado" + param.get("codigo"));
			return;
		}

		Map<String, BigDecimal> variaveis = new HashMap<String, BigDecimal>();

		variaveis.put("c", new BigDecimal(param.get("c").toString()));
		variaveis.put("q", new BigDecimal(param.get("q").toString()));

		System.out.println(String.format("%s %s", material.getCodigo(),
				replaceVariables(variaveis, material.getDescricao())));
		imprimeEstruturaMaterial(variaveis, material, " +");

	}

	private void imprimeEstruturaMaterial(Map<String, BigDecimal> variaveis,
			Material material, String tab) {

		for (MaterialItem item : material.getItem()) {
			item.getItem().getVariavel().putAll(variaveis);
			Expression exp = new Expression(item.getConsumoExpressao());
			exp.setVariables(item.getItem().getVariavel());
			item.getItem().getVariavel().put(item.getConsumoMedida().getCodigo(), exp.eval());
			System.out.println(String.format("%s %s %s [%.3f %s]", tab, item
					.getItem().getCodigo(),
					replaceVariables(variaveis, item.getItem().getDescricao()),
					item.getItem().getVariavel().get(item.getConsumoMedida().getCodigo()), item.getConsumoMedida().getUnidade()));
			for (Map.Entry<String, BigDecimal> v : item.getItem().getVariavel().entrySet()) {
				System.out.println(String.format("%s [%s=%.4f]", tab + " =>", v.getKey(), v.getValue()));
			}
			imprimeEstruturaMaterial(item.getItem().getVariavel(), item.getItem(), tab + "--");
		}
		
	}

	private String replaceVariables(Map<String, BigDecimal> variaveis,
			String texto) {
		for (Map.Entry<String, BigDecimal> e : variaveis.entrySet()) {
			texto = texto.replaceAll("\\#" + e.getKey() + "\\#", e.getValue().toString());
		}
		return texto;
	}
	
	
	@JmsListener(destination = "IHM-MATERIAL-CADASTRO")
	public void materialCadastro(String msg) throws JsonParseException, JsonMappingException, IOException {
		System.out.println(String.format("CHEGOU MENSAGEM DE IHM-MATERIAL-CADASTRO: %s", msg));
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> message = mapper.readValue(msg, Map.class);
	}
	
	@JmsListener(destination = "IHM-MATERIAL-MOVIMENTO")
	public void materialMovimento(String msg) throws JsonParseException, JsonMappingException, IOException {
		System.out.println(String.format("CHEGOU MENSAGEM DE IHM-MATERIAL-MOVIMENTO: %s", msg));
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> message = mapper.readValue(msg, Map.class);
	}
	
	@JmsListener(destination = "IHM-MATERIAL-TRANSFORMACAO")
	public void materialTransformacao(String msg) throws JsonParseException, JsonMappingException, IOException {
		System.out.println(String.format("CHEGOU MENSAGEM DE IHM-MATERIAL-TRANSFORMACAO: %s", msg));
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> message = mapper.readValue(msg, Map.class);
	}

}
