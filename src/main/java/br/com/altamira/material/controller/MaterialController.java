package br.com.altamira.material.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import javax.measure.unit.Unit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

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
import br.com.altamira.material.model.MaterialMovimentoTipo;
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
import br.com.altamira.material.repository.MaterialMovimentoRepository;
import br.com.altamira.material.repository.MaterialMovimentoTipoRepository;
import br.com.altamira.material.repository.MaterialRepository;
import br.com.altamira.material.repository.MedidaRepository;
import br.com.altamira.material.repository.UnidadeRepository;

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
			Expression exp = new Expression(materialMedida.getFormula());
			
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
			
			Expression exp = new Expression(materialMedida.getFormula());
			
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
			System.out.println(String.format("VALOR NAO RESOLVIDO: %s={%s}", materialMedida.getId().getMedida().getNome(), materialMedida.getFormula()));			
		}
		
		System.out.println(String.format("\n---------------------------------------------------------------------------------------------------\n| %1$15s %2$74s |\n---------------------------------------------------------------------------------------------------\n| %3$95s |\n---------------------------------------------------------------------------------------------------", material.getCodigo(),
				replaceVariables(variaveis, material.getDescricao()), "VARIAVEIS"));
		
		for (Map.Entry<String, BigDecimal> v : variaveis.entrySet()) {
			System.out.println(String.format("| %1$95s |", String.format("%1$20s = %2$25.10f", 
					v.getKey(), v.getValue())));
		}
		
		System.out.println(String.format("---------------------------------------------------------------------------------------------------\n| %1$95s |\n---------------------------------------------------------------------------------------------------", "MEDIDAS"));

		for (MaterialMedida materialMedida : material.getMedidas()) {
			
			Expression exp = new Expression(materialMedida.getFormula());
			
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
			Expression exp = new Expression(materialMedida.getFormula());
			
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
			
			Expression exp = new Expression(materialMedida.getFormula());
			
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
			System.out.println(String.format("VALOR NAO RESOLVIDO: %s={%s}", materialMedida.getId().getMedida().getNome(), materialMedida.getFormula()));			
		}

		for (MaterialComponente materialComponente : material.getComponentes()) {

			unsolved = new LinkedList<MaterialMedida>();
			
			for (MaterialMedida materialMedida : materialComponente.getId().getComponente().getMedidas()) {
				Expression exp = new Expression(materialMedida.getFormula());
				
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
				
				Expression exp = new Expression(materialMedida.getFormula());
				
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
				System.out.println(String.format("VALOR NAO RESOLVIDO: %s={%s}", materialMedida.getId().getMedida().getNome(), materialMedida.getFormula()));			
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
	@JmsListener(destination = "IHM-MATERIAL-MOVIMENTACAO")
	public void materialMovimento(String msg) throws JsonParseException,
			JsonMappingException, IOException, ParseException {
		System.out.println(String.format(
				"\n--------------------------------------------------------------------------------\nCHEGOU MENSAGEM DE IHM-MATERIAL-MOVIMENTACAO\n--------------------------------------------------------------------------------\n%s\n--------------------------------------------------------------------------------\n", msg));

		ObjectMapper mapper = new ObjectMapper();
		MovimentoMsg movimentoMsg = mapper.readValue(msg, MovimentoMsg.class);

		MaterialMovimento movimento = new MaterialMovimento(movimentoMsg.getDatahora(), movimentoMsg.getIHM().toUpperCase(), movimentoMsg.getOperador());

		materialMovimentoRepository.saveAndFlush(movimento);
		
		if (movimentoMsg.getMateriais() != null && !movimentoMsg.getMateriais().isEmpty()) {
			
			for (MaterialMsg materialMsg : movimentoMsg.getMateriais()) {
				
				Material material = null;
				
				MaterialMovimentoTipo movimentoTipo = materialMovimentoTipoRepository.findOne(materialMsg.getMovimentacao());
				
				MaterialInventario lote = materialInventarioRepository.findOne(new MaterialInventarioPK(materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero()));
				
				if (lote != null) {
					
					material = materialRepository.findOne(lote.getMaterial());
					
					if (movimentoTipo.getOperacao().toUpperCase().startsWith("E")) {
						lote.setLocal(materialMsg.getLocal());
						materialInventarioRepository.saveAndFlush(lote);
					}
					
				} else {
					
					if (movimentoTipo.getGerarLote()) {
						if (materialMsg.getCodigo().trim().length() > 0) {
							material = materialRepository.findOne(materialMsg.getCodigo());
							
							if (material != null) {
								lote = new MaterialInventario(new MaterialInventarioPK(materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero()), material.getCodigo(), materialMsg.getLocal());
								materialInventarioRepository.saveAndFlush(lote);
							} else {
								System.out.println(String.format(
										"\n****************************************************************************\n -----> NAO GEROU LOTE: %s-%s, MATERIAL NAO EXISTE [%s]\n****************************************************************************\n", materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero().toString(), materialMsg.getCodigo()));
							}
						} else {
							System.out.println(String.format(
									"\n****************************************************************************\n -----> NAO GEROU LOTE: %s-%s, CODIGO DO MATERIAL ESTA EM BRANCO\n****************************************************************************\n", materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero().toString(), materialMsg.getCodigo()));
						}
						
					} else {
						System.out.println(String.format(
							"\n****************************************************************************\n -----> NAO ENCONTROU LOTE: [%s-%s]\n****************************************************************************\n", materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero().toString()));
					}
				}
	
				if (lote != null && material != null) {
					
					MaterialMovimentoItem item = new MaterialMovimentoItem(movimento.getId(), materialMsg.getMovimentacao(), material.getCodigo(), lote.getId().getTipo(), lote.getId().getNumero(), materialMsg.getEmUso(), lote.getLocal());

					materialMovimentoItemRepository.saveAndFlush(item);
					
					//Map<String, BigDecimal> variaveis;

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
							

							
							MaterialInventarioMedida loteMedida = materialInventarioMedidaRepository.findOne(new MaterialInventarioMedidaPK(materialMsg.getLote().getTipo(), materialMsg.getLote().getNumero(), medida.getNome()));

							if (loteMedida == null) {
								
								loteMedida = new MaterialInventarioMedida(new MaterialInventarioMedidaPK(lote.getId().getTipo(), lote.getId().getNumero(), medida.getNome()), medidaMsg.getUnidade(), medidaMsg.getValor());
							
							} else {
								
								if (movimentoTipo.getAlteraSaldoLote()) {
									
									if (movimentoTipo.getOperacao().toUpperCase().startsWith("E")) {
										loteMedida.setValor(loteMedida.getValor().add(medidaMsg.getValor()));
									} else {
										loteMedida.setValor(loteMedida.getValor().subtract(medidaMsg.getValor()));
									}

								}									
							}
							
							materialInventarioMedidaRepository.saveAndFlush(loteMedida);

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
									"\n****************************************************************************\n -----> NAO ENCONTROU MEDIDA: [%s]\n****************************************************************************\n", medidaMsg.getMedida()));
						}
						
					}
						
				} else {
					System.out.println(String.format(
							"\n****************************************************************************\n -----> NAO ENCONTROU MATERIAL: [%s]\n****************************************************************************\n", materialMsg.getCodigo()));
				}
			}
		}

	}

}
