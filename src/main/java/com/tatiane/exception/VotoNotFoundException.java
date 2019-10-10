package com.tatiane.exception;

public class VotoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public VotoNotFoundException(String message, Throwable cause) {
		
	}

	public VotoNotFoundException() {
		super("O funcionário já votou na data de hoje no mesmo restaurante");
	}
	
	

}
