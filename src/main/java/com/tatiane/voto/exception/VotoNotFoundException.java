package com.tatiane.voto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class VotoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public VotoNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public VotoNotFoundException() {
		super("Funcionário não foi encontrado.");
	}

}
