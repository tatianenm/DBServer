package com.tatiane.restaurante.dto;

import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestauranteDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;

	private String nome;

	private String endereco;
	
}
