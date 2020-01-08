package com.tatiane.voto.exception;

public class VotoBusinessException extends RuntimeException {

    public static final String MSG_VOTO_REPETIDO = "O funcionário já votou na data de hoje no mesmo restaurante";

    public VotoBusinessException(){
        super(MSG_VOTO_REPETIDO);
    }

}
