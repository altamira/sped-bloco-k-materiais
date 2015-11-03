package br.com.altamira.material.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import br.com.altamira.data.wbccad.model.Prdorc;
import br.com.altamira.material.expression.Expression;
import br.com.altamira.material.model.Material;
import br.com.altamira.material.model.MaterialComponente;
import br.com.altamira.material.model.MaterialLote;
import br.com.altamira.material.model.MaterialLoteMedida;
import br.com.altamira.material.model.MaterialLoteMedidaPK;
import br.com.altamira.material.model.MaterialLotePK;
import br.com.altamira.material.model.MaterialMedida;
import br.com.altamira.material.model.MaterialMovimento;
import br.com.altamira.material.model.MaterialMovimentoItem;
import br.com.altamira.material.model.MaterialMovimentoItemMedida;
import br.com.altamira.material.model.MaterialMovimentoTipo;
import br.com.altamira.material.model.Medida;
import br.com.altamira.material.msg.MaterialMsg;
import br.com.altamira.material.msg.MedidaMsg;
import br.com.altamira.material.msg.MovimentoMsg;
import br.com.altamira.material.repository.MaterialComponenteRepository;
import br.com.altamira.material.repository.MaterialLoteMedidaRepository;
import br.com.altamira.material.repository.MaterialLoteRepository;
import br.com.altamira.material.repository.MaterialMedidaRepository;
import br.com.altamira.material.repository.MaterialMovimentoItemMedidaRepository;
import br.com.altamira.material.repository.MaterialMovimentoItemRepository;
import br.com.altamira.material.repository.MaterialMovimentoRepository;
import br.com.altamira.material.repository.MaterialMovimentoTipoRepository;
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
	private MaterialComponenteRepository materialComponenteRepository;
	
	@Autowired
	private MaterialMedidaRepository materialMedidaRepository;

	@Autowired
	private MedidaRepository medidaRepository;

	@Autowired
	private MaterialMovimentoRepository materialMovimentoRepository;
	
	@Autowired
	private MaterialLoteRepository materialLoteRepository;
	
	@Autowired
	private MaterialMovimentoItemRepository materialMovimentoItemRepository;
	
	@Autowired
	private MaterialLoteMedidaRepository materialLoteMedidaRepository;
	
	@Autowired
	private MaterialMovimentoItemMedidaRepository materialMovimentoItemMedidaRepository;
	
	@Autowired
	private MaterialMovimentoTipoRepository materialMovimentoTipoRepository;

	/**
	 * Importa lista de materiais
	 * @param prdorc
	 */
	@Transactional
	@JmsListener(destination = "IMPORT-MATERIAL")
	public void importaMaterial(Prdorc prdorc) {
		System.out.println("Recebido " + prdorc.getProduto());

		Material material = new Material();

		material.setCodigo(prdorc.getProduto());
		material.setDescricao(prdorc.getDescricao());
		material.setTipo("01");

		Material stored = materialRepository.saveAndFlush(material);

		System.out.println("Gravado ID " + stored.getCodigo());
	}

	@JmsListener(destination = "MATERIAL-MEDIDAS")
	public void calculaMaterialMedidas(String msg) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		MaterialMsg materialMsg = mapper.readValue(msg, MaterialMsg.class);
		
		Material material = materialRepository.findOne(materialMsg.getCodigo());

		if (material == null) {
			System.out.println("Codigo nao entrado: " + materialMsg.getCodigo());
			return;
		}

		Map<String, BigDecimal> variaveis = new HashMap<String, BigDecimal>();

		for (MedidaMsg medidaMsg : materialMsg.getMedidas()) {
			Medida medida = medidaRepository.findByNome(medidaMsg.getMedida());
			
			if (medida == null) {
				medida = medidaRepository.findByVariavel(medidaMsg.getMedida());
			}
			
			System.out.println(String.format("%s: %s=%s %s", medida.getNome(), medida.getVariavel(), medidaMsg.getValor(), medidaMsg.getUnidade()));
			
			if (medida != null) {
				variaveis.put(medida.getVariavel(), medidaMsg.getValor());
			}
		}

		Stack<MaterialMedida> unsolved = new Stack<MaterialMedida>();
		
		for (MaterialMedida materialMedida : material.getMedidas()) {
			Expression exp = new Expression(materialMedida.getFormula());
			
			exp.setVariables(variaveis);
			
			BigDecimal valor = null;
			
			try {
				valor = exp.setPrecision(20).eval();
			} catch(Exception e) {
				System.out.println(String.format("VALOR NAO RESOLVIDO: %s {%s}\n [%s]", materialMedida.getId().getMedida().getNome(), materialMedida.getFormula(), e.getMessage()));
				unsolved.push(materialMedida);
			}
			
			if (valor != null) {
				materialMedida.setValor(valor);
				variaveis.put(materialMedida.getId().getMedida().getNome(), materialMedida.getValor());
			}
			
		}
		
		while (!unsolved.empty()) {
			MaterialMedida materialMedida = unsolved.pop();
			
			Expression exp = new Expression(materialMedida.getFormula());
			
			exp.setVariables(variaveis);
			
			BigDecimal valor = null;
			
			try {
				valor = exp.setPrecision(20).eval();
			} catch(Exception e) {
				System.out.println(String.format("VALOR NAO RESOLVIDO: %s {%s}\n [%s]", materialMedida.getId().getMedida().getNome(), materialMedida.getFormula(), e.getMessage()));
				unsolved.add(materialMedida);
			}
			
			if (valor != null) {
				materialMedida.setValor(valor);
				variaveis.put(materialMedida.getId().getMedida().getNome(), materialMedida.getValor());
			}
		}
		
		System.out.println(String.format("\n----------------------------------------------------------------------------------------\n%s %s\n----------------------------------------------------------------------------------------\nVARIAVEIS\n----------------------------------------------------------------------------------------", material.getCodigo(),
				replaceVariables(variaveis, material.getDescricao())));
		
		for (Map.Entry<String, BigDecimal> v : variaveis.entrySet()) {
			System.out.println(String.format("%s=%.4f", 
					v.getKey(), v.getValue()));
		}
		
		System.out.println(String.format("\n----------------------------------------------------------------------------------------\nMEDIDAS\n----------------------------------------------------------------------------------------"));

		for (MaterialMedida materialMedida : material.getMedidas()) {
			
			Expression exp = new Expression(materialMedida.getFormula());
			
			exp.setVariables(variaveis);
			
			System.out.println(String.format("%s [%.3f %s]", materialMedida.getId().getMedida().getNome(), materialMedida.getValor() == null ? 0f : materialMedida.getValor().doubleValue(), materialMedida.getUnidade()));
		}

		/*materialComponente.getId().getComponente().getVariavel().putAll(variaveis);
		
		Expression exp = new Expression(materialComponente.getConsumoExpressao());
		
		exp.setVariables(materialComponente.getId().getComponente().getVariavel());
		
		materialComponente.getId().getComponente().getVariavel()
				.put(materialComponente.getConsumoMedida().getNome(), exp.eval());
		
		System.out.println(String.format(
				"%s %s %s [%.3f %s]",
				tab,
				materialComponente.getId().getComponente().getCodigo(),
				replaceVariables(variaveis, materialComponente.getId().getComponente()
						.getDescricao()),
						materialComponente.getId().getComponente().getVariavel()
						.get(materialComponente.getConsumoMedida().getNome()),
						materialComponente.getConsumoMedida().getUnidade()));
		
		for (Map.Entry<String, BigDecimal> v : materialComponente.getId().getComponente()
				.getVariavel().entrySet()) {
			System.out.println(String.format("%s [%s=%.4f]", tab + " =>",
					v.getKey(), v.getValue()));
		}*/
		
	}
	
	/**
	 * Calcula as variáveis das medidas dos materiais
	 * @param parameters
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@JmsListener(destination = "MATERIAL-LIST")
	public void calculaEstruturaMaterial(String parameters)
			throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper o = new ObjectMapper();

		@SuppressWarnings("unchecked")
		Map<String, Object> param = o.readValue(parameters, Map.class);

		Material material = materialRepository.findOne(param.get("codigo")
				.toString());

		if (material == null) {
			System.out.println("Codigo nao entrado: " + param.get("codigo"));
			return;
		}

		Map<String, BigDecimal> variaveis = new HashMap<String, BigDecimal>();

		variaveis.put("c", new BigDecimal(param.get("c").toString()));
		variaveis.put("q", new BigDecimal(param.get("q").toString()));

		System.out.println(String.format("%s %s", material.getCodigo(),
				replaceVariables(variaveis, material.getDescricao())));
		imprimeEstruturaMaterial(variaveis, material, " +");

	}

	/**
	 * Imprime a estrutura do Material
	 * @param variaveis
	 * @param material
	 * @param tab
	 */
	private void imprimeEstruturaMaterial(Map<String, BigDecimal> variaveis,
			Material material, String tab) {

		for (MaterialComponente materialComponente : material.getComponentes()) {
			
			materialComponente.getId().getComponente().getVariavel().putAll(variaveis);
			
			Expression exp = new Expression(materialComponente.getConsumoExpressao());
			
			exp.setVariables(materialComponente.getId().getComponente().getVariavel());
			
			materialComponente.getId().getComponente().getVariavel()
					.put(materialComponente.getConsumoMedida().getNome(), exp.eval());
			
			System.out.println(String.format(
					"%s %s %s [%.3f %s]",
					tab,
					materialComponente.getId().getComponente().getCodigo(),
					replaceVariables(variaveis, materialComponente.getId().getComponente()
							.getDescricao()),
							materialComponente.getId().getComponente().getVariavel()
							.get(materialComponente.getConsumoMedida().getNome()),
							materialComponente.getConsumoMedida().getUnidade()));
			
			for (Map.Entry<String, BigDecimal> v : materialComponente.getId().getComponente()
					.getVariavel().entrySet()) {
				System.out.println(String.format("%s [%s=%.4f]", tab + " =>",
						v.getKey(), v.getValue()));
			}
			
			imprimeEstruturaMaterial(materialComponente.getId().getComponente().getVariavel(),
					materialComponente.getId().getComponente(), tab + "--");
		}

	}

	/**
	 * Substitui as variaveis por valores
	 * @param variaveis
	 * @param texto
	 * @return
	 */
	private String replaceVariables(Map<String, BigDecimal> variaveis,
			String texto) {
		for (Map.Entry<String, BigDecimal> e : variaveis.entrySet()) {
			texto = texto.replaceAll("\\#" + e.getKey() + "\\#", e.getValue()
					.toString());
		}
		return texto;
	}

	/**
	 * Esta rotina é responsável pela movimentacao e calculo de saldos de estoque dos materiais
	 * 
	 * @param msg 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws ParseException
	 */
	@Transactional
	@JmsListener(destination = "IHM-MATERIAL")
	public void materialMovimento(String msg) throws JsonParseException,
			JsonMappingException, IOException, ParseException {
		System.out.println(String.format(
				"\n--------------------------------------------------------------------------------\nCHEGOU MENSAGEM DE IHM-MATERIAL-MOVIMENTO\n--------------------------------------------------------------------------------\n%s\n--------------------------------------------------------------------------------\n", msg));

		ObjectMapper mapper = new ObjectMapper();
		MovimentoMsg movimentoMsg = mapper.readValue(msg, MovimentoMsg.class);

		MaterialMovimento movimento = new MaterialMovimento(movimentoMsg.getDatahora(), movimentoMsg.getMaquina().toUpperCase(), movimentoMsg.getOperador());

		materialMovimentoRepository.saveAndFlush(movimento);
		
		if (movimentoMsg.getMateriais() != null && !movimentoMsg.getMateriais().isEmpty()) {
			
			for (MaterialMsg materialMsg : movimentoMsg.getMateriais()) {
				
				MaterialMovimentoTipo movimentoTipo = materialMovimentoTipoRepository.findOne(materialMsg.getMovimentacao());
				
				Material material = materialRepository.findOne(materialMsg.getCodigo());
	
				if (material != null) {
					
					MaterialMovimentoItem item = new MaterialMovimentoItem(movimento.getId(), materialMsg.getMovimentacao(), materialMsg.getCodigo(), materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero(), materialMsg.getEmUso(), materialMsg.getLocal());

					materialMovimentoItemRepository.saveAndFlush(item);
					
					MaterialLote lote = materialLoteRepository.findOne(new MaterialLotePK(materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero()));
					
					if (lote != null) {
						
						if (movimentoTipo.getOperacao().toUpperCase().startsWith("E")) {
							lote.setLocal(materialMsg.getLocal());
						}
						
					} else {
						
						if (movimentoTipo.getGerarLote()) {
							lote = new MaterialLote(new MaterialLotePK(materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero()), material.getCodigo(), materialMsg.getLocal());
						} else {
							System.out.println(String.format(
								"\n****************************************************************************\n -----> NAO ENCONTROU/GEROU LOTE: %s-%s\n****************************************************************************\n", materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero().toString()));
						}
					}

					materialLoteRepository.saveAndFlush(lote);

					Map<String, BigDecimal> variaveis;
					
					for (MedidaMsg medidaMsg : materialMsg.getMedidas()) {
						
						Medida medida = medidaRepository.findByDescricao(medidaMsg.getMedida());
						
						if (medida != null) {

							/*
							variaveis.put(medida.getCodigo(), medidaMsg.getValor());
							
							material.getVariavel().putAll(variaveis);
							
							Expression exp = new Expression(material.getMedidas(). Expressao());
							
							exp.setVariables(materialComponente.getId().getComponente().getVariavel());
							
							materialComponente.getId().getComponente().getVariavel()
									.put(materialComponente.getConsumoMedida().getCodigo(), exp.eval());
							
							System.out.println(String.format(
									"%s %s %s [%.3f %s]",
									tab,
									materialComponente.getId().getComponente().getCodigo(),
									replaceVariables(variaveis, materialComponente.getId().getComponente()
											.getDescricao()),
											materialComponente.getId().getComponente().getVariavel()
											.get(materialComponente.getConsumoMedida().getCodigo()),
											materialComponente.getConsumoMedida().getUnidade()));
							*/
							

							
							MaterialLoteMedida loteMedida = materialLoteMedidaRepository.findOne(new MaterialLoteMedidaPK(materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero(), medida.getNome()));

							if (loteMedida == null) {
								
								loteMedida = new MaterialLoteMedida(new MaterialLoteMedidaPK(materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero(), medida.getNome()), medidaMsg.getUnidade(), medidaMsg.getValor());
							
							} else {
								
								if (movimentoTipo.getAlteraSaldoLote()) {
									
									if (movimentoTipo.getOperacao().toUpperCase().startsWith("E")) {
										loteMedida.setValor(loteMedida.getValor().add(medidaMsg.getValor()));
									} else {
										loteMedida.setValor(loteMedida.getValor().subtract(medidaMsg.getValor()));
									}

								}									
							}
							
							materialLoteMedidaRepository.saveAndFlush(loteMedida);

							MaterialMovimentoItemMedida itemMedida = new MaterialMovimentoItemMedida(item.getId(), medida, medidaMsg.getUnidade(), medidaMsg.getValor());
							
							materialMovimentoItemMedidaRepository.saveAndFlush(itemMedida);
							
							if (movimentoTipo.getAlteraSaldoMaterial()) {
								
								if (movimentoTipo.getOperacao().toUpperCase().startsWith("E")) {
									//material.setEstoque(material.getEstoque().add(medidaMsg.getValor()));
									
								} else {
									//material.setEstoque(material.getEstoque().subtract(medidaMsg.getValor()));
								}
								
								materialRepository.saveAndFlush(material);
							}
							
						} else {
							System.out.println(String.format(
									"\n****************************************************************************\n -----> NAO ENCONTROU MEDIDA: %s\n****************************************************************************\n", medidaMsg.getMedida()));
						}
						
					}
					
				} else {
					System.out.println(String.format(
							"\n****************************************************************************\n -----> NAO ENCONTROU MATERIAL: %s\n****************************************************************************\n", materialMsg.getCodigo()));
				}
			}
		}

	}

}
