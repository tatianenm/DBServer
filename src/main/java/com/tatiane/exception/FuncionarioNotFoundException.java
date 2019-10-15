package com.tatiane.exception;

public class FuncionarioNotFoundException  extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public FuncionarioNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public FuncionarioNotFoundException() {
		super("O funcionário não foi encontrado.");
	}
	
	

}
