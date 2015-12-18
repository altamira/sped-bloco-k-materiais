package br.com.altamira.material.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.measure.unit.Unit;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.altamira.data.wbccad.model.Prdorc;
import br.com.altamira.material.expression.Expression;
import br.com.altamira.material.model.ConversaoUnidade;
import br.com.altamira.material.model.ConversaoUnidadePK;
import br.com.altamira.material.model.Lote;
import br.com.altamira.material.model.Material;
import br.com.altamira.material.model.MaterialComponente;
import br.com.altamira.material.model.MaterialInventario;
import br.com.altamira.material.model.MaterialInventarioMedida;
import br.com.altamira.material.model.MaterialInventarioMedidaPK;
import br.com.altamira.material.model.MaterialInventarioPK;
import br.com.altamira.material.model.MaterialMedida;
import br.com.altamira.material.model.MaterialMovimento;
import br.com.altamira.material.model.MaterialMovimentoItem;
import br.com.altamira.material.model.MaterialMovimentoItemMedida;
import br.com.altamira.material.model.MaterialMovimentoLogErro;
import br.com.altamira.material.model.MaterialMovimentoTipo;
import br.com.altamira.material.model.MaterialVariavel;
import br.com.altamira.material.model.Medida;
import br.com.altamira.material.model.Unidade;
import br.com.altamira.material.msg.MaterialMsg;
import br.com.altamira.material.msg.MedidaMsg;
import br.com.altamira.material.msg.MovimentoMsg;
import br.com.altamira.material.repository.ConversaoUnidadeRepository;
import br.com.altamira.material.repository.MaterialComponenteRepository;
import br.com.altamira.material.repository.MaterialInventarioMedidaRepository;
import br.com.altamira.material.repository.MaterialInventarioRepository;
import br.com.altamira.material.repository.MaterialMedidaRepository;
import br.com.altamira.material.repository.MaterialMovimentoItemMedidaRepository;
import br.com.altamira.material.repository.MaterialMovimentoItemRepository;
import br.com.altamira.material.repository.MaterialMovimentoLogErroRepository;
import br.com.altamira.material.repository.MaterialMovimentoRepository;
import br.com.altamira.material.repository.MaterialMovimentoTipoRepository;
import br.com.altamira.material.repository.MaterialRepository;
import br.com.altamira.material.repository.MaterialVariavelRepository;
import br.com.altamira.material.repository.MedidaRepository;
import br.com.altamira.material.repository.UnidadeRepository;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//@Controller
@RestController
@Transactional
@RequestMapping("/material")
public class MaterialController {
	
	@Autowired
	private MaterialRepository materialRepository;

	@Autowired
	private MaterialComponenteRepository materialComponenteRepository;
	
	@Autowired
	private MaterialMedidaRepository materialMedidaRepository;
	
	@Autowired
	private MaterialVariavelRepository materialVariavelRepository;

	@Autowired
	private MedidaRepository medidaRepository;

	@Autowired
	private MaterialMovimentoRepository materialMovimentoRepository;
	
	@Autowired
	private MaterialInventarioRepository materialInventarioRepository;
	
	@Autowired
	private MaterialMovimentoItemRepository materialMovimentoItemRepository;
	
	@Autowired
	private MaterialInventarioMedidaRepository materialInventarioMedidaRepository;
	
	@Autowired
	private MaterialMovimentoItemMedidaRepository materialMovimentoItemMedidaRepository;
	
	@Autowired
	private MaterialMovimentoTipoRepository materialMovimentoTipoRepository;
	
	@Autowired 
	private ConversaoUnidadeRepository conversaoUnidadeRepository;
	
	@Autowired
	private MaterialMovimentoLogErroRepository materialMovimentoLogErroRepository;
	
	@Autowired
	private UnidadeRepository unidadeRepository;

	@JmsListener(destination = "UNIT-CONVERTER")
	public void unitConverter(String msg) {
        String fromUnit = "kg/m^3";
        String toUnit = "kg/mm^3";
        
        double valor = 7856d;
        
        System.out.println(String.format("\nUnit Measure from %s to %s %.10f\n\n", fromUnit, toUnit, convert(valor, "kg/m^3", "kg/mm^3")));	
	}

	private double convert(double value, String fromUnit, String toUnit) {
        return Unit.valueOf(fromUnit)
                .getConverterTo(Unit.valueOf(toUnit))
                .convert(value);
    }
	
	@JmsListener(destination = "ECO")
	public void echo(String msg) {
		System.out.println("\n\nMensagem recebida:\n-->" + msg.trim() + "<--");
	}
	
	@JmsListener(destination = "MATERIAL-DV-TESTE")
	public void materialDV(String numeros) {
		System.out.println("\n\nIniciando teste dos digitos verificadores");
		
		String[] nums = numeros.trim().replace(" ", "").replace("\n", "").replace("\r", "").split(";"); 
		
		for (String num : nums) {
			String numero = num.replace("-", "");
			numero = numero.substring(0, numero.length() - 2);
			int modulo10 = Lote.getModulo10(numero);
			int modulo11 = Lote.getModulo11(numero);
			
			int mod10 = Integer.parseInt(num.substring(num.length() - 2, num.length() - 1));
			int mod11 = Integer.parseInt(num.substring(num.length() - 1, num.length()));
			
			if (mod10 != modulo10 && mod11 != modulo11) {
				System.out.println(String.format("\nDigito invalido: %s -> DV: %d.%d", num, modulo10, modulo11));	
			}
			
		}
		
		System.out.println(String.format("\nFim do teste, %d numeros foram verificados\n", nums.length));
	}
	
	/**
	 * Importa lista de materiais
	 * @param prdorc
	 */
	@Transactional
	@JmsListener(destination = "MATERIAL-IMPORTAR")
	public void importaMaterial(Prdorc prdorc) {
		System.out.println("Recebido " + prdorc.getProduto());

		Material material = new Material();

		material.setCodigo(prdorc.getProduto());
		material.setDescricao(prdorc.getDescricao());
		material.setTipo("01");

		Material stored = materialRepository.saveAndFlush(material);

		System.out.println("Gravado ID " + stored.getCodigo());
	}

	private BigDecimal converteUnidade(String unidadeOrigem, String unidadeDestino, BigDecimal valor) throws Exception {
		if (unidadeOrigem.equalsIgnoreCase(unidadeDestino)) {
			return valor;
		}
		
		Unidade uOrigem = unidadeRepository.findBySimbolo(unidadeOrigem);

		if (uOrigem == null) {
			throw new Exception(String.format("NAO ENCONTROU A UNIDADE DE MEDIDA %s", unidadeOrigem));
		}
		
		Unidade uDestino = unidadeRepository.findBySimbolo(unidadeDestino);
		
		if (uDestino == null) {
			throw new Exception(String.format("NAO ENCONTROU A UNIDADE DE MEDIDA %s", unidadeDestino));
		}
		
		ConversaoUnidade convesaoUnidade = conversaoUnidadeRepository.findById(new ConversaoUnidadePK(uOrigem, uDestino));
		
		if (convesaoUnidade == null) {
			throw new Exception(String.format("NAO ENCONTROU UMA CONVERSAO DE UNIDADES COMPATIVEL COM [%s <-> %s]", unidadeOrigem, unidadeDestino));
		}
		
		BigDecimal valorConvertido = valor.multiply(convesaoUnidade.getFator());
		
		return valorConvertido;
	}
	
	@JmsListener(destination = "MATERIAL-MEDIDAS")
	public void calculaMaterialMedidas(String msg) throws Exception {
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
			
			if (medida != null) {
				BigDecimal valor = converteUnidade(medidaMsg.getUnidade(), medida.getUnidade(), medidaMsg.getValor());
				System.out.println(String.format("%s: %s=%s %s", medida.getNome(), medida.getVariavel(), valor, medida.getUnidade()));
				variaveis.put(medida.getVariavel(), valor);
			} else {
				System.out.println(String.format("MEDIDA/VARIAVEL NAO ENCONTRADA: %s=[%.4f %s]", medidaMsg.getMedida(), medidaMsg.getValor().doubleValue(), medidaMsg.getUnidade()));			
			}
		}

		Queue<MaterialMedida> unsolved = new LinkedList<MaterialMedida>();
		
		for (MaterialMedida materialMedida : material.getMedidas()) {
			Expression exp = new Expression(materialMedida.getConsumo());
			
			exp.setVariables(variaveis);
			
			BigDecimal valor = null;
			
			try {
				valor = exp.setPrecision(10).eval();
			} catch(Exception e) {
				unsolved.add(materialMedida);
			}
			
			if (valor != null) {
				materialMedida.setValor(valor);
				variaveis.put(materialMedida.getId().getMedida().getNome(), materialMedida.getValor());
			}
			
		}
		
		// calcula o numero máximo de iteracoes até resolver todas as dependencias entre as variaveis
		int unsolvedCount = (int)Math.pow((double)unsolved.size(), 2);
		
		while (!unsolved.isEmpty() && unsolvedCount > 0) {
			MaterialMedida materialMedida = unsolved.remove();
			
			Expression exp = new Expression(materialMedida.getConsumo());
			
			exp.setVariables(variaveis);
			
			BigDecimal valor = null;
			
			try {
				valor = exp.setPrecision(10).eval();
			} catch(Exception e) {
				unsolved.add(materialMedida);
				unsolvedCount--;
			}
			
			if (valor != null) {
				materialMedida.setValor(valor);
				variaveis.put(materialMedida.getId().getMedida().getNome(), materialMedida.getValor());
			}
		}
		
		while (!unsolved.isEmpty()) {
			MaterialMedida materialMedida = unsolved.remove();
			System.out.println(String.format("VALOR NAO RESOLVIDO: %s={%s}", materialMedida.getId().getMedida().getNome(), materialMedida.getConsumo()));			
		}
		
		System.out.println(String.format("\n---------------------------------------------------------------------------------------------------\n| %1$15s %2$74s |\n---------------------------------------------------------------------------------------------------\n| %3$95s |\n---------------------------------------------------------------------------------------------------", material.getCodigo(),
				replaceVariables(variaveis, material.getDescricao()), "VARIAVEIS"));
		
		for (Map.Entry<String, BigDecimal> v : variaveis.entrySet()) {
			System.out.println(String.format("| %1$95s |", String.format("%1$20s = %2$25.10f", 
					v.getKey(), v.getValue())));
		}
		
		System.out.println(String.format("---------------------------------------------------------------------------------------------------\n| %1$95s |\n---------------------------------------------------------------------------------------------------", "MEDIDAS"));

		for (MaterialMedida materialMedida : material.getMedidas()) {
			
			Expression exp = new Expression(materialMedida.getConsumo());
			
			exp.setVariables(variaveis);
			
			System.out.println(String.format("| %1$95s |", String.format("%1$20s [%2$25.10f %3$10s]", materialMedida.getId().getMedida().getNome(), materialMedida.getValor() == null ? 0f : materialMedida.getValor().doubleValue(), materialMedida.getUnidade())));
		}

		System.out.println(String.format("---------------------------------------------------------------------------------------------------\n"));
		
	}
	
	/**
	 * Calcula as variáveis das medidas dos materiais
	 * @param parameters
	 * @throws Exception 
	 */
	@JmsListener(destination = "MATERIAL-ESTRUTURA")
	public void calculaEstruturaMaterial(String msg)
			throws Exception {
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
			
			if (medida != null) {
				BigDecimal valor = converteUnidade(medidaMsg.getUnidade(), medida.getUnidade(), medidaMsg.getValor());
				System.out.println(String.format("%s: %s=%s %s", medida.getNome(), medida.getVariavel(), valor, medida.getUnidade()));
				variaveis.put(medida.getVariavel(), valor);
			} else {
				System.out.println(String.format("MEDIDA/VARIAVEL NAO ENCONTRADA: %s=[%.4f %s]", medidaMsg.getMedida(), medidaMsg.getValor().doubleValue(), medidaMsg.getUnidade()));			
			}
		}

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
		
		Queue<MaterialMedida> unsolved = new LinkedList<MaterialMedida>();
		
		for (MaterialMedida materialMedida : material.getMedidas()) {
			Expression exp = new Expression(materialMedida.getConsumo());
			
			exp.setVariables(variaveis);
			
			BigDecimal valor = null;
			
			try {
				valor = exp.setPrecision(10).eval();
			} catch(Exception e) {
				unsolved.add(materialMedida);
			}
			
			if (valor != null) {
				materialMedida.setValor(valor);
				variaveis.put(materialMedida.getId().getMedida().getNome(), materialMedida.getValor());
			}
			
		}
		
		// calcula o numero máximo de iteracoes até resolver todas as dependencias entre as variaveis
		int unsolvedCount = (int)Math.pow((double)unsolved.size(), 2);
		
		while (!unsolved.isEmpty() && unsolvedCount > 0) {
			MaterialMedida materialMedida = unsolved.remove();
			
			Expression exp = new Expression(materialMedida.getConsumo());
			
			exp.setVariables(variaveis);
			
			BigDecimal valor = null;
			
			try {
				valor = exp.setPrecision(10).eval();
			} catch(Exception e) {
				unsolved.add(materialMedida);
				unsolvedCount--;
			}
			
			if (valor != null) {
				materialMedida.setValor(valor);
				variaveis.put(materialMedida.getId().getMedida().getNome(), materialMedida.getValor());
			}
		}
		
		while (!unsolved.isEmpty()) {
			MaterialMedida materialMedida = unsolved.remove();
			System.out.println(String.format("VALOR NAO RESOLVIDO: %s={%s}", materialMedida.getId().getMedida().getNome(), materialMedida.getConsumo()));			
		}

		for (MaterialComponente materialComponente : material.getComponentes()) {

			unsolved = new LinkedList<MaterialMedida>();
			
			for (MaterialMedida materialMedida : materialComponente.getId().getComponente().getMedidas()) {
				Expression exp = new Expression(materialMedida.getConsumo());
				
				exp.setVariables(variaveis);
				
				BigDecimal valor = null;
				
				try {
					valor = exp.setPrecision(10).eval();
				} catch(Exception e) {
					unsolved.add(materialMedida);
				}
				
				if (valor != null) {
					materialMedida.setValor(valor);
					variaveis.put(materialMedida.getId().getMedida().getNome(), materialMedida.getValor());
				}
				
			}
			
			// calcula o numero máximo de iteracoes até resolver todas as dependencias entre as variaveis
			unsolvedCount = (int)Math.pow((double)unsolved.size(), 2);
			
			while (!unsolved.isEmpty() && unsolvedCount > 0) {
				MaterialMedida materialMedida = unsolved.remove();
				
				Expression exp = new Expression(materialMedida.getConsumo());
				
				exp.setVariables(variaveis);
				
				BigDecimal valor = null;
				
				try {
					valor = exp.setPrecision(10).eval();
				} catch(Exception e) {
					unsolved.add(materialMedida);
					unsolvedCount--;
				}
				
				if (valor != null) {
					materialMedida.setValor(valor);
					variaveis.put(materialMedida.getId().getMedida().getNome(), materialMedida.getValor());
				}
			}
			
			while (!unsolved.isEmpty()) {
				MaterialMedida materialMedida = unsolved.remove();
				System.out.println(String.format("VALOR NAO RESOLVIDO: %s={%s}", materialMedida.getId().getMedida().getNome(), materialMedida.getConsumo()));			
			}
			
			materialComponente.getId().getComponente().getVariavel().putAll(variaveis);
			
			Expression exp = new Expression(materialComponente.getConsumoExpressao());
			
			exp.setVariables(materialComponente.getId().getComponente().getVariavel());
			
			materialComponente.getId().getComponente().getVariavel()
					.put(materialComponente.getConsumoMedida().getNome(), exp.setPrecision(10).eval());
			
			System.out.println(String.format(
					"%s %s %s [%.10f %s]",
					tab,
					materialComponente.getId().getComponente().getCodigo(),
					replaceVariables(variaveis, materialComponente.getId().getComponente()
							.getDescricao()),
							materialComponente.getId().getComponente().getVariavel()
							.get(materialComponente.getConsumoMedida().getNome()),
							materialComponente.getConsumoMedida().getUnidade()));
			
			for (Map.Entry<String, BigDecimal> v : materialComponente.getId().getComponente()
					.getVariavel().entrySet()) {
				System.out.println(String.format("%s [%s=%.10f]", tab + " =>",
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
	@RequestMapping(value = "/movimentacao", method = RequestMethod.POST)
	@JmsListener(destination = "IHM-MATERIAL-MOVIMENTACAO")
	public void materialMovimento(@RequestBody String msg) throws JsonParseException,
			JsonMappingException, IOException, ParseException {
		System.out.println(String.format(
				"\n--------------------------------------------------------------------------------\nCHEGOU MENSAGEM DE IHM-MATERIAL-MOVIMENTACAO\n--------------------------------------------------------------------------------\n%s\n--------------------------------------------------------------------------------\n", msg));

		ObjectMapper mapper = new ObjectMapper();
		MovimentoMsg movimentoMsg = mapper.readValue(msg, MovimentoMsg.class);

		// Registra a transação de movimentação de estoque
		MaterialMovimento movimento = new MaterialMovimento(movimentoMsg.getDatahora(), movimentoMsg.getIHM().toUpperCase(), movimentoMsg.getOperador());

		materialMovimentoRepository.saveAndFlush(movimento);
		
		if (movimentoMsg.getMateriais() != null && !movimentoMsg.getMateriais().isEmpty()) {
			
			for (MaterialMsg materialMsg : movimentoMsg.getMateriais()) {
				
				Material material = null;
				
				MaterialMovimentoTipo movimentoTipo = materialMovimentoTipoRepository.findOne(materialMsg.getMovimentacao());
				
				if (movimentoTipo == null) {
					System.out.println(String.format(
							"\n****************************************************************************\n TIPO DE MOVIMENTO INVALIDO: %s\n****************************************************************************\n", materialMsg.getMovimentacao()));
					
					MaterialMovimentoLogErro erro = new MaterialMovimentoLogErro(new Date(), movimento.getId(), String.format("TIPO DE MOVIMENTO INVALIDO: %s", materialMsg.getMovimentacao()), msg);
					materialMovimentoLogErroRepository.saveAndFlush(erro);
					
					return;
				}
				
				MaterialInventario lote = materialInventarioRepository.findOne(new MaterialInventarioPK(materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero()));
				
				if (lote != null) {
					
					material = materialRepository.findOne(lote.getMaterial());
					
					//if (movimentoTipo.getOperacao().toUpperCase().trim().equals("E")) {
						lote.setLocal(materialMsg.getLocal().toUpperCase());
						materialInventarioRepository.saveAndFlush(lote);
					//}
					
				} else {
					
					if (movimentoTipo.getGerarLote()) {
						if (materialMsg.getCodigo().trim().length() > 0) {
							material = materialRepository.findOne(materialMsg.getCodigo());
							
							if (material != null) {
								lote = new MaterialInventario(new MaterialInventarioPK(materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero()), material.getCodigo().toUpperCase(), materialMsg.getLocal().toUpperCase());
								materialInventarioRepository.saveAndFlush(lote);
							} else {
								System.out.println(String.format(
										"\n****************************************************************************\n NAO GEROU LOTE: %s-%s, MATERIAL NAO EXISTE [%s]\n****************************************************************************\n", materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero().toString(), materialMsg.getCodigo()));
								
								MaterialMovimentoLogErro erro = new MaterialMovimentoLogErro(new Date(), movimento.getId(), String.format("NAO GEROU LOTE: %s-%s, MATERIAL NAO EXISTE [%s]", materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero().toString(), materialMsg.getCodigo()), msg);
								materialMovimentoLogErroRepository.saveAndFlush(erro);
								
							}
						} else {
							System.out.println(String.format(
									"\n****************************************************************************\n NAO GEROU LOTE: %s-%s, CODIGO DO MATERIAL ESTA EM BRANCO\n****************************************************************************\n", materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero().toString(), materialMsg.getCodigo().toUpperCase()));
							
							MaterialMovimentoLogErro erro = new MaterialMovimentoLogErro(new Date(), movimento.getId(), String.format("NAO GEROU LOTE: %s-%s, CODIGO DO MATERIAL ESTA EM BRANCO", materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero().toString(), materialMsg.getCodigo().toUpperCase()), msg);
							materialMovimentoLogErroRepository.saveAndFlush(erro);
							
						}
						
					} else {
						System.out.println(String.format(
							"\n****************************************************************************\n NAO ENCONTROU LOTE: [%s-%s]\n****************************************************************************\n", materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero().toString()));
						
						MaterialMovimentoLogErro erro = new MaterialMovimentoLogErro(new Date(), movimento.getId(), String.format("NAO ENCONTROU LOTE: [%s-%s]", materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero().toString()), msg);
						materialMovimentoLogErroRepository.saveAndFlush(erro);
						
					}
				}
	
				if (lote != null && material != null) {
					
					Map<String, BigDecimal> variaveis = new HashMap<String, BigDecimal>();

					Queue<MaterialMedida> unsolved = new LinkedList<MaterialMedida>();
					
					// calcula variaveis
					for (MedidaMsg medidaMsg : materialMsg.getMedidas()) {
						MaterialVariavel variavel = materialVariavelRepository.findByIdMaterialCodigoAndIdMedidaNome(material.getCodigo(), medidaMsg.getMedida());
						
						if (variavel != null) {
							variaveis.put(variavel.getVariavel(), medidaMsg.getValor());
							System.out.println(String.format("Material: %s, variavel: %s, valor: %f", material.getCodigo(), variavel.getVariavel(), medidaMsg.getValor()));
						}
					}
					
					List<MaterialMedida> materialMedidas = materialMedidaRepository.findByIdMaterialCodigo(material.getCodigo());
					
					// calcula medidas
					for (MaterialMedida materialMedida : materialMedidas) {
						Expression exp = new Expression(materialMedida.getConsumo());
						
						exp.setVariables(variaveis);
						
						BigDecimal valor = null;
						
						try {
							valor = exp.setPrecision(10).eval();
						} catch(Exception e) {
							unsolved.add(materialMedida);
							/*System.out.println(String.format(
									"\n****************************************************************************\n A EXPRESSAO NAO PODE SER AVALIADA: %s [%s] {%s}\n Erro: %s\n****************************************************************************\n", material.getCodigo(), materialMedida.getId().getMedida().getNome(), materialMedida.getConsumo(), e.getMessage()));*/
							
							/*
							MaterialMovimentoLogErro erro = new MaterialMovimentoLogErro(new Date(), movimento.getId(), String.format("A EXPRESSAO NAO PODE SER AVALIADA: %s [%s] {%s}", material.getCodigo(), materialMedida.getId().getMedida().getNome(), materialMedida.getConsumo()), msg);
							materialMovimentoLogErroRepository.saveAndFlush(erro);
							*/
							
						}
						
						if (valor != null) {
							materialMedida.setValor(valor);
							variaveis.put(materialMedida.getId().getMedida().getNome(), materialMedida.getValor());
						}
						
					}
					
					int iteracoes = 0;
					
					/* para evitar loop infinito, caso a expressão não tenha solução
					 * calcula o número máximo de iteracoes necessárias 
					 * para resolver todas as dependeências entre as variaveis
					 */
					for (int i = unsolved.size(); i > 0; i--) {
						iteracoes += i;
					}
									
					while (!unsolved.isEmpty() && iteracoes > 0) {
						MaterialMedida materialMedida = unsolved.remove();
						
						Expression consumo = new Expression(materialMedida.getConsumo());
						
						consumo.setVariables(variaveis);
						
						BigDecimal valor = null;
						
						try {
							valor = consumo.setPrecision(10).eval();
						} catch(Exception e) {
							unsolved.add(materialMedida);
							iteracoes--;
						}
						
						if (valor != null) {
							materialMedida.setValor(valor);
							variaveis.put(materialMedida.getId().getMedida().getNome(), materialMedida.getValor());
							/*System.out.println(String.format(
									"\n****************************************************************************\n A EXPRESSAO FOI RESOLVIDA: %s [%s] {%s} = %f\n Quant. de Iterações necessárias: %d\n****************************************************************************\n", material.getCodigo(), materialMedida.getId().getMedida().getNome(), materialMedida.getConsumo(), materialMedida.getValor(), iteracoes));*/
							
							/*
							MaterialMovimentoLogErro erro = new MaterialMovimentoLogErro(new Date(), movimento.getId(), String.format("A EXPRESSAO FOI RESOLVIDA: %s [%s] {%s} = %f", material.getCodigo(), materialMedida.getId().getMedida().getNome(), materialMedida.getConsumo(), materialMedida.getValor()), msg);
							materialMovimentoLogErroRepository.saveAndFlush(erro);
							*/
							
						}
					}
					
					while (!unsolved.isEmpty()) {
						MaterialMedida materialMedida = unsolved.remove();
						System.out.println(String.format(
								"\n****************************************************************************\n VALOR NAO RESOLVIDO: %s={%s}\n****************************************************************************\n", material.getCodigo(), materialMedida.getId().getMedida().getNome()));
						
						MaterialMovimentoLogErro erro = new MaterialMovimentoLogErro(new Date(), movimento.getId(), String.format("VALOR NAO RESOLVIDO: %s={%s}", material.getCodigo(), materialMedida.getId().getMedida().getNome()), msg);
						materialMovimentoLogErroRepository.saveAndFlush(erro);
						
					}

					// calcula medidas
					/*for (MaterialMedida materialMedida : materialMedidas) {
						
						Medida medida = medidaRepository.findByDescricao(materialMedida.getId().getMedida().getNome());
						
						if (medida != null) {

							//MaterialMedida materialMedida = materialMedidaRepository.findByIdMaterialCodigoAndIdMedidaNome(material.getCodigo(), medida.getNome());
							
							if (materialMedida != null) {
								
								material.getVariavel().putAll(variaveis);
								
								Expression consumo = new Expression(materialMedida.getConsumo());
								
								consumo.setVariables(variaveis);
								
								try {
									BigDecimal valor = consumo.eval();	
									variaveis.put(materialMedida.getId().getMedida().getNome(), valor);
								} catch (Exception e) {
									System.out.println(String.format(
											"\n****************************************************************************\n A EXPRESSAO NAO PODE SER AVALIADA: %s [%s] \n****************************************************************************\nErro: %s", material.getCodigo(), medida.getNome(), e.getMessage()));
								}
								
							}
						}
					}*/

					MaterialMovimentoItem item = new MaterialMovimentoItem(movimento.getId(), materialMsg.getMovimentacao(), material.getCodigo(), lote.getId().getTipo(), lote.getId().getNumero(), materialMsg.getEmUso(), lote.getLocal());

					materialMovimentoItemRepository.saveAndFlush(item);
					
					// atualiza saldo inventario e insere medida no movimento de estoque
					for (MaterialMedida materialMedida : materialMedidas) {
						
						//Medida medida = medidaRepository.findByDescricao(medidaMsg.getMedida());
						
						//if (medida != null) {

							//MaterialMedida materialMedida = materialMedidaRepository.findByIdMaterialCodigoAndIdMedidaNome(material.getCodigo(), medida.getNome());
							
							//if (materialMedida != null) {

								MaterialInventarioMedida loteMedida = materialInventarioMedidaRepository.findOne(new MaterialInventarioMedidaPK(lote.getId().getTipo(), lote.getId().getNumero(), materialMedida.getId().getMedida().getNome()));
								
								if (loteMedida == null) {
									loteMedida = new MaterialInventarioMedida(
											new MaterialInventarioMedidaPK(
													lote.getId().getTipo(), 
													lote.getId().getNumero(), 
													materialMedida.getId().getMedida().getNome()),
											materialMedida.getUnidade(),
											materialMedida.getValor());
								} else {
								
									if (movimentoTipo.getAlteraSaldoLote() && materialMedida.getSaldo()) {
										
										if (movimentoTipo.getOperacao().toUpperCase().startsWith("E")) {
											loteMedida.setValor(loteMedida.getValor().add(materialMedida.getValor()));
										} else if (movimentoTipo.getOperacao().toUpperCase().startsWith("S")) {
											loteMedida.setValor(loteMedida.getValor().subtract(materialMedida.getValor()));
										} else {
											loteMedida.setValor(materialMedida.getValor());
										}
	
									}		

								}
								
								materialInventarioMedidaRepository.saveAndFlush(loteMedida);

								MaterialMovimentoItemMedida itemMedida = new MaterialMovimentoItemMedida(item.getId(), materialMedida.getId().getMedida(), materialMedida.getId().getMedida().getUnidade(), materialMedida.getValor());
								
								materialMovimentoItemMedidaRepository.saveAndFlush(itemMedida);
								
								/*
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
										"\n****************************************************************************\n NAO ENCONTROU MATERIAL MEDIDA: %s [%s]\n****************************************************************************\n", material.getCodigo(), medida.getNome()));
							}
							
						} else {
							System.out.println(String.format(
									"\n****************************************************************************\n NAO ENCONTROU MEDIDA: [%s]\n****************************************************************************\n", medidaMsg.getMedida()));
						}
						*/
						
					}
						
				} else {
					System.out.println(String.format(
							"\n****************************************************************************\n LOTE OU MATERIAL NAO ENCONTRADO: LOTE:[%s, %s], MATERIAL: %s\n****************************************************************************\n", materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero(), materialMsg.getCodigo()));
					
					MaterialMovimentoLogErro erro = new MaterialMovimentoLogErro(new Date(), movimento.getId(), String.format("LOTE OU MATERIAL NAO ENCONTRADO: LOTE:[%s, %s], MATERIAL: %s", materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero(), materialMsg.getCodigo()), msg);
					materialMovimentoLogErroRepository.saveAndFlush(erro);
					
				}
			}
		} else {
			System.out.println(String.format(
					"\n****************************************************************************\n MOVIMENTACAO SEM MATERIAL\n****************************************************************************\n"));
			
			MaterialMovimentoLogErro erro = new MaterialMovimentoLogErro(new Date(), movimento.getId(), "MOVIMENTACAO SEM MATERIAL", msg);
			materialMovimentoLogErroRepository.saveAndFlush(erro);
			
		}

	}
	
	@PersistenceContext
	EntityManager entityManager;
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@RequestMapping("/inventario")
	public List<Map<String, Object>> Inventario() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		List data = sessionFactory.getCurrentSession().createSQLQuery("exec INVENTARIO").setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
		
		for (Object object : data)
		{
			Map row = (Map)object;
			
			System.out.println(row);
			list.add(row);

		}
		
		
		
		/*entityManager.creates
		//.createNativeQuery("exec INVENTARIO");  
		javax.persistence.Query query = entityManager.createNamedStoredProcedureQuery("inventario").getr
		
		@SuppressWarnings("unchecked")
		List<Object[]> result = query.;
		
		for (Object[] fields : result) {
			
			for (Object field : fields) {
				System.out.println(field);
			}

		}*/
		
		
		
		return list;
	}
	
}
