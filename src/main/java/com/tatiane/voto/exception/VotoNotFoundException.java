package com.tatiane.voto.exception;

public class VotoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	
	public VotoNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public VotoNotFoundException(String message) {
		super(message);
	}

}
