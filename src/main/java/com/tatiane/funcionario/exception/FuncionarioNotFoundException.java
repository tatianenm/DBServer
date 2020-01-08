package com.tatiane.funcionario.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class FuncionarioNotFoundException  extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public FuncionarioNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public FuncionarioNotFoundException() {
		super("Funcionário não foi encontrado.");
	}
	
	

}
