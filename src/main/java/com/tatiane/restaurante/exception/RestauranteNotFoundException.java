package com.tatiane.restaurante.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RestauranteNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public RestauranteNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public RestauranteNotFoundException() {
		super("O restaurante não foi encontrado.");
	}

}
