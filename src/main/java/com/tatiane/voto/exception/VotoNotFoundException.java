package com.tatiane.voto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class VotoNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public static final String MSG_FUNCIONARIO_NAO_ENCONTRADO = "Voto não foi encontrado.";

	public VotoNotFoundException() {
		super(MSG_FUNCIONARIO_NAO_ENCONTRADO);
	}

}
