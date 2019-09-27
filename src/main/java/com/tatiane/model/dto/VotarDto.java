package com.tatiane.model.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VotarDto implements Serializable {

	private static final long serialVersionUID = 4646409658092662335L;

	private Integer idRestaurante;

	private Integer idFuncionario;

}
