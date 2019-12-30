package com.tatiane.model.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VotarDto implements Serializable {

	private static final long serialVersionUID = 4646409658092662335L;

	private Integer idRestaurante;

	private Integer idFuncionario;
	
	private Boolean escolhido;

}
