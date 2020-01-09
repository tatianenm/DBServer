package com.tatiane.voto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
public class VotoBusinessException extends RuntimeException {

    public VotoBusinessException(String message){
        super(message);
    }

}
