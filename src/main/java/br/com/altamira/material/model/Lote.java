package br.com.altamira.material.model;

public class Lote {

	private String tipo;
	
	private Long numero;

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Long getNumero() {
		return numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}
	
	public static int getModulo11(Integer numero) {  
	    int[] pesos = {4, 3, 2, 9, 8, 7, 6, 5};   
	    String n = numero.toString();
	    int somaPonderada = 0;   
	    
	    for (int i = 0; i < n.length(); i++){  
	        somaPonderada += pesos[i % 8] * (Integer.parseInt(n.substring(i, i + 1)));  
	    }
	    
	    return 11 - somaPonderada % 11;    
	} 
	
	/**
	 * Rotina para calculo do modulo 10 otimizada
	 * Calcula qualquer string numerica ou não 
	 * @param numero
	 * @return
	 */
	public static int getModulo10(String numero) {
		int mul = numero.length() % 2 + 1;
		int soma = 0;

		for (char num : numero.toCharArray()) {  
			int fator = (num - '0') * mul;
			soma += fator > 9 ? (fator - 9) : fator;
			mul = mul == 2 ? 1 : 2;
		}

		return soma == 10 ? 0 : 10 - (soma % 10);
	}

}
