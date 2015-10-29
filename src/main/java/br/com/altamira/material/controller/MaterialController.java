package br.com.altamira.material.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
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
import br.com.altamira.material.model.MaterialComponente;
import br.com.altamira.material.model.MaterialConsumo;
import br.com.altamira.material.model.MaterialLote;
import br.com.altamira.material.model.MaterialLotePK;
import br.com.altamira.material.model.MaterialPerda;
import br.com.altamira.material.model.MaterialProducao;
import br.com.altamira.material.model.Medida;
import br.com.altamira.material.model.MovimentacaoEstoque;
import br.com.altamira.material.msg.MaterialCadastroMsg;
import br.com.altamira.material.msg.MaterialMovimentoMsg;
import br.com.altamira.material.msg.MaterialMsg;
import br.com.altamira.material.msg.MedidaMsg;
import br.com.altamira.material.repository.MaterialComponenteRepository;
import br.com.altamira.material.repository.MaterialConsumoRepository;
import br.com.altamira.material.repository.MaterialLoteRepository;
import br.com.altamira.material.repository.MaterialPerdaRepository;
import br.com.altamira.material.repository.MaterialProducaoRepository;
import br.com.altamira.material.repository.MaterialRepository;
import br.com.altamira.material.repository.MedidaRepository;
import br.com.altamira.material.repository.MovimentacaoEstoqueRepository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MaterialController {

	@Autowired
	private MaterialRepository materialRepository;

	@Autowired
	private MaterialComponenteRepository materialComponenteRepository;

	@Autowired
	private MedidaRepository medidaRepository;

	@Autowired
	private MovimentacaoEstoqueRepository movimentacaoEstoqueRepository;

	@Autowired
	private MaterialProducaoRepository materialProducaoRepository;

	@Autowired
	private MaterialConsumoRepository materialConsumoRepository;

	@Autowired
	private MaterialPerdaRepository materialPerdaRepository;
	
	@Autowired
	private MaterialLoteRepository materialLoteRepository;

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
	public void calculaEstruturaMaterial(String parameters)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper o = new ObjectMapper();

		@SuppressWarnings("unchecked")
		Map<String, Object> param = o.readValue(parameters, Map.class);

		Material material = materialRepository.findOne(param.get("codigo")
				.toString());

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

		for (MaterialComponente componente : material.getComponente()) {
			componente.getComponente().getVariavel().putAll(variaveis);
			Expression exp = new Expression(componente.getConsumoExpressao());
			exp.setVariables(componente.getComponente().getVariavel());
			componente.getComponente().getVariavel()
					.put(componente.getConsumoMedida().getCodigo(), exp.eval());
			System.out.println(String.format(
					"%s %s %s [%.3f %s]",
					tab,
					componente.getComponente().getCodigo(),
					replaceVariables(variaveis, componente.getComponente()
							.getDescricao()),
					componente.getComponente().getVariavel()
							.get(componente.getConsumoMedida().getCodigo()),
					componente.getConsumoMedida().getUnidade()));
			for (Map.Entry<String, BigDecimal> v : componente.getComponente()
					.getVariavel().entrySet()) {
				System.out.println(String.format("%s [%s=%.4f]", tab + " =>",
						v.getKey(), v.getValue()));
			}
			imprimeEstruturaMaterial(componente.getComponente().getVariavel(),
					componente.getComponente(), tab + "--");
		}

	}

	private String replaceVariables(Map<String, BigDecimal> variaveis,
			String texto) {
		for (Map.Entry<String, BigDecimal> e : variaveis.entrySet()) {
			texto = texto.replaceAll("\\#" + e.getKey() + "\\#", e.getValue()
					.toString());
		}
		return texto;
	}

	@JmsListener(destination = "IHM-MATERIAL-CADASTRO")
	public void materialCadastro(String msg) throws JsonParseException,
			JsonMappingException, IOException {
		System.out.println(String.format(
				"\n--------------------------------------------------------------------------------\nCHEGOU MENSAGEM DE IHM-MATERIAL-CADASTRO\n--------------------------------------------------------------------------------\n%s\n--------------------------------------------------------------------------------\n", msg));
		
		ObjectMapper mapper = new ObjectMapper();
		MaterialCadastroMsg mensagem = mapper.readValue(msg, MaterialCadastroMsg.class);
		
		System.out.println(String.format(
				"\n********************************************************************************\n -----> CODIGO MATERIAL: [%s] %s\n********************************************************************************\n", mensagem.getDatahora().toString(), mensagem.getMaterial().getCodigo()));
		
		Material material = materialRepository.findOne(mensagem.getMaterial().getCodigo());
		
		List<MedidaMsg> medida = mensagem.getMaterial().getMedida();

		Medida m = medidaRepository.findByDescricao(medida.get(0).getMedida());
		
		MaterialLote lote = materialLoteRepository.findOne(new MaterialLotePK(mensagem.getMaterial().getRastreamento().getTipo(), mensagem.getMaterial().getRastreamento().getNumero()));
		
		if (lote == null) {
			lote = new MaterialLote(new MaterialLotePK(mensagem.getMaterial().getRastreamento().getTipo(), mensagem.getMaterial().getRastreamento().getNumero()), material.getCodigo(), m.getCodigo(), medida.get(0).getUnidade(), medida.get(0).getValor());	
			materialLoteRepository.saveAndFlush(lote);

			material.setEstoque(material.getEstoque().add(medida.get(0).getValor()));
			materialRepository.saveAndFlush(material);
		} else {
			System.out.println(String.format(
				"\n********************************************************************************\n -----> CADASTRO DE LOTE JA EXISTE: %s-%s\n********************************************************************************\n", mensagem.getMaterial().getRastreamento().getTipo(), mensagem.getMaterial().getRastreamento().getNumero().toString()));
		}

	}

	@JmsListener(destination = "IHM-MATERIAL-MOVIMENTO")
	public void materialMovimento(String msg) throws JsonParseException,
			JsonMappingException, IOException, ParseException {
		System.out.println(String.format(
				"\n--------------------------------------------------------------------------------\nCHEGOU MENSAGEM DE IHM-MATERIAL-MOVIMENTO\n--------------------------------------------------------------------------------\n%s\n--------------------------------------------------------------------------------\n", msg));

		ObjectMapper mapper = new ObjectMapper();
		MaterialMovimentoMsg mensagem = mapper.readValue(msg, MaterialMovimentoMsg.class);

		MovimentacaoEstoque mov = new MovimentacaoEstoque(mensagem.getMaquina(), mensagem.getOperador());

		movimentacaoEstoqueRepository.saveAndFlush(mov);
		
		if (mensagem.getProducao() != null) {
			
			MaterialMsg producao = mensagem.getProducao();

			Material material = materialRepository.findOne(producao.getCodigo());

			if (material != null) {
				
				List<MedidaMsg> medida = producao.getMedida();

				Medida m = medidaRepository.findByDescricao(medida.get(0).getMedida());

				if (m != null) {

					MaterialProducao p = new MaterialProducao(
							mov.getId(),
							producao.getCodigo(), 
							producao.getRastreamento().getTipo() + "-" + producao.getRastreamento().getNumero().toString(), 
							m.getCodigo(), 
							medida.get(0).getUnidade(), 
							medida.get(0).getValor());

					materialProducaoRepository.saveAndFlush(p);
					
					MaterialLote lote = new MaterialLote(new MaterialLotePK(producao.getRastreamento().getTipo(), producao.getRastreamento().getNumero()), material.getCodigo(), m.getCodigo(), medida.get(0).getUnidade(), medida.get(0).getValor());
					
					materialLoteRepository.saveAndFlush(lote);

					material.setEstoque(material.getEstoque().add(medida.get(0).getValor()));
					materialRepository.saveAndFlush(material);

				}

			}

		}

		if (mensagem.getConsumo() != null && !mensagem.getConsumo().isEmpty()) {
			
			for (MaterialMsg consumo : mensagem.getConsumo()) {
				
				Material material = materialRepository.findOne(consumo.getCodigo());
	
				if (material != null) {
					
					List<MedidaMsg> medida = consumo.getMedida();

					Medida m = medidaRepository.findByDescricao(medida.get(0).getMedida());
	
					if (m != null) {
	
						MaterialLote lote = materialLoteRepository.findOne(new MaterialLotePK(consumo.getRastreamento().getTipo(), consumo.getRastreamento().getNumero()));
						
						if (lote != null) {
							lote.setValor(lote.getValor().subtract(medida.get(0).getValor()));
							materialLoteRepository.saveAndFlush(lote);
						} else {
							System.out.println(String.format(
									"\n****************************************************************************\n -----> NAO ENCONTROU LOTE: %s-%s\n****************************************************************************\n", consumo.getRastreamento().getTipo(), consumo.getRastreamento().getNumero().toString()));
						}
						
						MaterialConsumo c = new MaterialConsumo(
								mov.getId(),
								consumo.getCodigo(), 
								consumo.getRastreamento().getTipo() + "-" + consumo.getRastreamento().getNumero().toString(), 
								m.getCodigo(), 
								medida.get(0).getUnidade(), 
								medida.get(0).getValor());
	
						materialConsumoRepository.saveAndFlush(c);
						
						material.setEstoque(material.getEstoque().subtract(medida.get(0).getValor()));
						materialRepository.saveAndFlush(material);
					}
				}
			}
			
		}
		
		if (mensagem.getPerda() != null) {
			
			MaterialMsg perda = mensagem.getPerda();

			Material material = materialRepository.findOne(perda.getCodigo());

			if (material != null) {
				
				List<MedidaMsg> medida = perda.getMedida();

				Medida m = medidaRepository.findByDescricao(medida.get(0).getMedida());

				if (m != null) {

					MaterialPerda p = new MaterialPerda(
							mov.getId(),
							perda.getCodigo(), 
							perda.getRastreamento().getTipo() + "-" + perda.getRastreamento().getNumero().toString(), 
							m.getCodigo(), 
							medida.get(0).getUnidade(), 
							medida.get(0).getValor());

					materialPerdaRepository.saveAndFlush(p);
					
					MaterialLote lote = new MaterialLote(new MaterialLotePK(perda.getRastreamento().getTipo(), perda.getRastreamento().getNumero()), material.getCodigo(), m.getCodigo(), medida.get(0).getUnidade(), medida.get(0).getValor());
					
					materialLoteRepository.saveAndFlush(lote);

					material.setEstoque(material.getEstoque().subtract(medida.get(0).getValor()));
					materialRepository.saveAndFlush(material);

				}

			}

		}

	}

	@JmsListener(destination = "IHM-MATERIAL-TRANSFORMACAO")
	public void materialTransformacao(String msg) throws JsonParseException,
			JsonMappingException, IOException {
		System.out.println(String.format(
				"CHEGOU MENSAGEM DE IHM-MATERIAL-TRANSFORMACAO: %s", msg));
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> message = mapper.readValue(msg, Map.class);
	}

}
