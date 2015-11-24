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
	
	public static int getModulo11(String numero) {  
	    int soma = 0;   
	    
	    for (int i = numero.length() - 1; i >= 0; i--) {  
	    	soma += (numero.charAt(i) - '0') * (((numero.length() - 1) - i) % 8 + 2);  
	    }
	    
	    int digito = soma % 11; 
	    
	    if(digito > 1) {
			digito = 11 - digito;
		} else {
			digito = 0;
		}
	    
	    return digito;    
	} 
	
	/**
	 * Rotina para calculo do modulo 10 otimizada
	 * Calcula qualquer string numerica ou não 
	 * @param numero
	 * @return
	 */
	public static int getModulo10(String numero) {
		int soma = 0;
		
		if (numero.length() < 6) {
			numero = numero.trim();
		}
		for (int i = numero.length() - 1; i >= 0; i--) {  
			int fator = (numero.charAt(i) - '0') * ((numero.length() - i) % 2 + 1);
			do {
				soma += fator % 10;
				fator /= 10;
			} while(fator > 0);
		}
		int digito = 10 - (soma % 10);
		
		return digito == 10 ? 0 : digito;
	}

}
