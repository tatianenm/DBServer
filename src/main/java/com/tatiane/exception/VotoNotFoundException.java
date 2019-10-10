package com.tatiane.exception;

public class VotoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public static final String MSG_RESTAURANTE_REPETIDO = "O restaurante escolhido já foi selecionado esta semana.";
	
	public VotoNotFoundException(String message, Throwable cause) {
		
	}

	public VotoNotFoundException() {
		super("O funcionário já votou na data de hoje no mesmo restaurante");
	}
	
	

}
