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
	
	public static int getModulo10(Integer numero) {
		int i = (numero.toString().length() % 2) + 1;
		int s = 0;
		
		for (char c : numero.toString().toCharArray()) {  
			int r = (c - '0') * i;
			s += r > 9 ? (r - 9) : r;
			i = i == 2 ? 1 : 2;
		}

		return s == 10 ? 0 : 10 - (s % 10);
	}

}
